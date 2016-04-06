package net.bondar

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import net.bondar.impl.*
import net.bondar.interfaces.*
import net.bondar.services.APIService

/**
 * Script for returns information about closest places on the basic of specified geographical coordinates.
 */
//args = ["-p","48.4644455,35.0475775,2"] /*--> OK*/
//args = ["-h"] /*--> help*/
try {
    AbstractUrlBuilderFactory urlBuilderFactory = new GPAUrlBuilderFactory()
    AbstractProcessorFactory processorFactory = new APIProcessorFactory()
    APIConnection apiConnection = new GooglePlacesAPI()
    ObjectChecker objectChecker = new GPAObjectChecker()
    JSONConverter jsonConverter = new GroovyJSONConverter(new JsonBuilder(), new JsonSlurper())
    ParameterVerifier parameterVerifier = new GPASearchParameterVerifier()
    OptionViewer optionViewer = new CliOptionsViewer()
    InputVerifier inputVerifier = new InputVerifier(optionViewer, parameterVerifier)

    def service = new APIService(urlBuilderFactory, processorFactory, apiConnection, objectChecker, jsonConverter, parameterVerifier, optionViewer, inputVerifier)
    service.search(inputVerifier.verifyInput(args.size() > 0 ? args : ["-h"]))
} catch (Throwable t) {
    t.printStackTrace()
}
