package net.bondar.services

import groovy.util.logging.Log
import net.bondar.exceptions.LocationCheckerException
import net.bondar.impl.ParameterChecker
import net.bondar.interfaces.APIConnection
import net.bondar.interfaces.AbstractUrlBuilderFactory
import net.bondar.interfaces.JSONConverter
import net.bondar.interfaces.ObjectConverter
import net.bondar.models.ResultObject

/**
 * Processes the user search requests to obtain result object.
 */
@Log
class APIService {

    private AbstractUrlBuilderFactory urlBuilderFactory
    private APIConnection apiConnection
    private ObjectConverter objectConverter
    private JSONConverter jConverter
    private ParameterChecker paramsChecker

    APIService(AbstractUrlBuilderFactory urlBuilderFactory, APIConnection apiConnection, ObjectConverter objectConverter, JSONConverter jsonConverter, ParameterChecker parameterChecker) {
        this.urlBuilderFactory = urlBuilderFactory
        this.apiConnection = apiConnection
        this.objectConverter = objectConverter
        this.jConverter = jsonConverter
        this.paramsChecker = parameterChecker
    }

    /**
     * Processes user search requests.
     *
     * @param request the request is represented by a JSON object, which should have the following format: ..,
     * @return result object with places or with an error message
     */
    Object search(def request) {
        String latitude = request.latitude
        String longitude = request.longitude
        def placeCount = Integer.parseInt(request.count)
        log.info("Input parameters -->\nlatitude=${latitude}\nlongitude=${longitude}\nplaceCount=${placeCount}")

        try {
            // builds GPA url
//            log.info("Builds GPA url")
//            URL url = urlBuilderFactory.createUrlBuilder(latitude, longitude, null).build()
            // receives response object from GPA
            log.info("Receives response object from GPA")
//            Object responseObject = convertToObject(apiConnection.getInputStream(url))
            // checks places
            log.info("Processes places")
            ProcessingService checkingService = new ProcessingService(latitude, longitude, placeCount)
            def tmpObject = checkingService.process()
            log.info("Converting result object to JSON")
            ResultObject result = new ResultObject(tmpObject.status)
            result.addPlaces(tmpObject.places)
            convertToJSON(result)
        } catch (LocationCheckerException e) {
            log.info("Error while searching places")
            return new ResultObject("ERROR", "Error while searching places:\n ${e.getMessage()}")
        }
    }

    /**
     * Converts Objects to JSON.
     *
     * @param object object that needs to convert
     * @return converted JSON
     */
    Object convertToJSON(Object object) {
        jConverter.toJson(object)
    }

    /**
     * Converts JSON to Object.
     *
     * @param json json string that needs to convert
     * @return converted object
     */
    Object convertToObject(InputStreamReader json) {
        jConverter.toObject(json)
    }
}
