package net.bondar

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import net.bondar.exceptions.ApplicationException
import net.bondar.impl.*
import net.bondar.interfaces.*
import net.bondar.services.APIService
import org.junit.BeforeClass
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.fail

class ConsoleTest {
    static def apiService
    static def inputVerifier
    def args

    @BeforeClass
    static void start() {
        AbstractUrlBuilderFactory urlBuilderFactory = new GPAUrlBuilderFactory()
        AbstractProcessorFactory processorFactory = new APIProcessorFactory()
        APIConnection apiConnection = new GooglePlacesAPI()
        ObjectChecker objectChecker = new GPAObjectChecker()
        JSONConverter jsonConverter = new GroovyJSONConverter(new JsonBuilder(), new JsonSlurper())
        ParameterVerifier parameterVerifier = new GPASearchParameterVerifier()
        OptionViewer optionViewer = new CliOptionsViewer()
        inputVerifier = new InputVerifier(optionViewer, parameterVerifier)
        apiService = new APIService(urlBuilderFactory, processorFactory, apiConnection, objectChecker, jsonConverter, parameterVerifier, optionViewer, inputVerifier)
    }

    @Test
    void testEmptyOrNotFullArguments() {
        args = ["-p",]
        try {
            inputVerifier.verifyInput(args)
            fail()
        } catch (ApplicationException e) {
            assertEquals("Error empty arguments. Please check your input.", e.message)
        }
        args = ["-aaaaaa 123.450,181.200,-1",]
        try {
            inputVerifier.verifyInput(args)
            fail()
        } catch (ApplicationException e) {
            assertEquals("Wrong parameters or options. Please check your input.", e.getMessage())
        }
    }

    @Test
    void testWrongArguments() {
        def list = new ArrayList();
        list.add(["-p", "91.111;180.222;a"]);
        list.add(["-p", "90.b;180.000;3"]);
        list.add(["-p", "13.cde;180.123456789;4"]);
        list.add(["-p", "48.-;176.123456;y"]);
        list.add(["-p", "45.483521;95.fg;-1"]);
        list.add(["-p", "45.123456;125.345;-100"]);
        list.add(["-p", "45.438342;abcd;13"]);
        list.each {
            try {
                inputVerifier.verifyInput(it);
            } catch (ApplicationException e) {
                assertEquals("Wrong format of arguments. Please check your arguments. Example --38.453,46.455,3", e.getMessage());
            }
        }
    }

    @Test
    void testCorrectArguments() {
        assertEquals("[latitude:48.000, longitude:131.000, count:3]", inputVerifier.verifyInput(["-p", "48.000;131.000;3"]).toString())
        assertEquals("[latitude:90, longitude:180, count:1]", inputVerifier.verifyInput(["-p", "90;180;1"]).toString())
        assertEquals("[latitude:49.1, longitude:50.6, count:11]", inputVerifier.verifyInput(["-p", "49.1;50.6;11"]).toString())
        assertEquals("[latitude:45.438, longitude:46.538, count:25]", inputVerifier.verifyInput(["-p", "45.438;46.538;25"]).toString())
        assertEquals("[latitude:40.438342, longitude:41.538, count:40]", inputVerifier.verifyInput(["-p", "40.438342;41.538;40"]).toString())
    }

    @Test
    void testSearch() {
        def list = new ArrayList();
        list.add(["-p", "48.000;131.000;3"]);
        list.add(["-p", "90;180;1"]);
        list.add(["-p", "49.1;50.6;11"]);
        list.add(["-p", "45.438;46.538;25"]);
        list.add(["-p", "40.438342;41.538;40"]);
        list.each {
            def result = apiService.search(inputVerifier.verifyInput(it))
            assertEquals("[OK]", result.status.toString())
        }
    }
}


