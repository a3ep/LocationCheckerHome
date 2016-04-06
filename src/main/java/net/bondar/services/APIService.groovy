package net.bondar.services

import groovy.util.logging.Log4j
import net.bondar.impl.GPAObjectChecker
import net.bondar.impl.InputVerifier
import net.bondar.interfaces.*
import net.bondar.models.InputObject
import net.bondar.models.ResultObject

/**
 * Processes the user search requests to obtain result object.
 */
@Log4j
class APIService {

    private AbstractUrlBuilderFactory urlBuilderFactory
    private AbstractProcessorFactory processorFactory
    private APIConnection apiConnection
    private GPAObjectChecker objectChecker
    private JSONConverter jConverter
    private ParameterVerifier paramsVerifier
    private OptionViewer optionViewer
    private InputVerifier inputVerifier

    APIService(AbstractUrlBuilderFactory urlBuilderFactory,
               AbstractProcessorFactory processorFactory,
               APIConnection apiConnection,
               GPAObjectChecker objectChecker,
               JSONConverter jsonConverter,
               ParameterVerifier parameterVerifier,
               OptionViewer optionViewer,
               InputVerifier inputVerifier) {
        this.urlBuilderFactory = urlBuilderFactory
        this.processorFactory = processorFactory
        this.apiConnection = apiConnection
        this.objectChecker = objectChecker
        this.jConverter = jsonConverter
        this.paramsVerifier = parameterVerifier
        this.optionViewer = optionViewer
        this.inputVerifier = inputVerifier
    }

    /**
     * Processes user search requests.
     *
     * @param request the request is represented by a map, which should have the following format: latitude, longitude, count
     * @return result object with places or with an error message in JSON format
     */
    Object search(def request) {
        if (request instanceof ResultObject) {
            return convertToJSON(request)
        }
        String latitude = request.latitude.replace("--", "-")
        String longitude = request.longitude.replace("--", "-")
        def placeCount = Integer.parseInt(request.count)
        log.info("Input parameters -->\nlatitude=${latitude}\nlongitude=${longitude}\nplaceCount=${placeCount}\n")

        convertToJSON(buildResultObject(processorFactory.createProcessor(latitude, longitude, placeCount, apiConnection, objectChecker, jConverter).process()))
    }

    /**
     * Builds result object on the basis of InputObject.
     *
     * @param tempResultObject the object witch contains required information
     * @return complete result object
     */
    ResultObject buildResultObject(InputObject inputObject) {
        log.info("Builds result object\n")
        if (inputObject.status == 'OK') {
            if (inputObject.comment) {
                return new ResultObject(inputObject.status, inputObject.comment)
            }
            return new ResultObject(inputObject.status, inputObject.places)
        } else {
            return new ResultObject(inputObject.errorMessage)
        }
    }

    /**
     * Converts Objects to JSON.
     *
     * @param object object that needs to convert
     * @return converted JSON
     */
    Object convertToJSON(Object object) {
        log.info("Converting result object to JSON\n")
        def result = jConverter.toJson(object)
        log.info("The result of serching is --> ${result.toString()}")
        return result
    }
}
