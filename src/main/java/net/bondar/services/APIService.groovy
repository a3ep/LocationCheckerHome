package net.bondar.services

import groovy.util.logging.Log
import net.bondar.impl.GPAObjectChecker
import net.bondar.impl.ParameterChecker
import net.bondar.interfaces.APIConnection
import net.bondar.interfaces.AbstractProcessorFactory
import net.bondar.interfaces.AbstractUrlBuilderFactory
import net.bondar.interfaces.JSONConverter
import net.bondar.models.ResultObject
import net.bondar.models.TempResultObject

/**
 * Processes the user search requests to obtain result object.
 */
@Log
class APIService {

    private AbstractUrlBuilderFactory urlBuilderFactory
    private AbstractProcessorFactory processorFactory
    private APIConnection apiConnection
    private GPAObjectChecker objectChecker
    private JSONConverter jConverter
    private ParameterChecker paramsChecker

    APIService(AbstractUrlBuilderFactory urlBuilderFactory, AbstractProcessorFactory processorFactory, APIConnection apiConnection, GPAObjectChecker objectChecker, JSONConverter jsonConverter, ParameterChecker parameterChecker) {
        this.urlBuilderFactory = urlBuilderFactory
        this.processorFactory = processorFactory
        this.apiConnection = apiConnection
        this.objectChecker = objectChecker
        this.jConverter = jsonConverter
        this.paramsChecker = parameterChecker
    }

    /**
     * Verifies provided arguments.
     *
     * @param params request parameters: latitude, longitude and max count of places
     * @return map with coordinates and count of places
     */
    def checkInput(def params) {
        def options
        CliBuilder cli = new CliBuilder(usage: 'APIService.groovy -p [params]', header: 'Options:')
        cli.h(longOpt: "help", 'Print this message')
        cli.p(longOpt: "param", args: 3, valueSeparator: ';', argName: 'latitude, longitude, maxResultCount', 'Searching location with given coordinates. Negative numbers looks like --34.586')
        options = cli.parse(params)
        if (options.h || !options || !options.p) {
            cli.usage()
            System.exit(0)
        } else if (paramsChecker.check(["latitude": "${options.ps[0]}", "longitude": "${options.ps[1]}"])) {
            ["latitude": "${options.ps[0].trim()}", "longitude": "${options.ps[1].trim()}", "count": "${options.ps[2]}"]
        }
    }

    /**
     * Processes user search requests.
     *
     * @param request the request is represented by a map, which should have the following format: latitude, longitude, count
     * @return result object with places or with an error message in JSON format
     */
    Object search(def request) {
        String latitude = request.latitude.replace("--", "-")
        String longitude = request.longitude.replace("--", "-")
        def placeCount = Integer.parseInt(request.count)
        log.info("Input parameters -->\nlatitude=${latitude}\nlongitude=${longitude}\nplaceCount=${placeCount}")

        convertToJSON(buildResultObject(processorFactory.createProcessor(latitude, longitude, placeCount, apiConnection, objectChecker, jConverter).process()))
    }

    /**
     * Builds result object on the basis of TempResultObject.
     *
     * @param tempResultObject the object witch contains required information
     * @return complete result object
     */
    ResultObject buildResultObject(TempResultObject tempResultObject) {
        log.info("Builds result object")
        if (tempResultObject.status == 'OK') {
            if (tempResultObject.comment != "") {
                new ResultObject(tempResultObject.status, tempResultObject.comment, "")
            }
            ResultObject resultObject = new ResultObject("OK")
            resultObject.addPlaces(tempResultObject.places)
            return resultObject
        } else {
            new ResultObject(tempResultObject.status, tempResultObject.errorMessage)
        }
    }

    /**
     * Converts Objects to JSON.
     *
     * @param object object that needs to convert
     * @return converted JSON
     */
    Object convertToJSON(Object object) {
        log.info("Converting result object to JSON")
        jConverter.toJson(object)
    }
}
