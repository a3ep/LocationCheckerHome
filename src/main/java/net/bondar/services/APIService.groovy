package net.bondar.services

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Log
import net.bondar.impl.*
import net.bondar.interfaces.DataChecker
import net.bondar.interfaces.DataParser
import net.bondar.interfaces.JsonConverterInt
import net.bondar.interfaces.UrlBuilder
import net.bondar.models.ResultObject

/**
 * Main service which work with user requests and passing tasks to another classes
 */
@Log
class APIService {

    private UrlBuilder urlBuilder
    private DataChecker dataChecker
    private DataParser dataParser
    private JsonConverterInt jConverter
    private ParamsChecker paramsChecker

    APIService() {
        this.dataChecker = new GpaDataChecker()
        this.dataParser = new GpaDataParser()
        this.jConverter = new GroovyJsonConverter(new JsonBuilder(), new JsonSlurper())
        this.paramsChecker = new ParamsChecker()
    }

    /**
     * Processes user search requests.
     *
     * @param request the request is represented by a JSON object, which should have the following format: ..,
     * @return result object with places or with an error message
     */
    def search(def request) {
        // checks input params
        log.info("Checking input params")
        ParamsChecker paramsChecker = new ParamsChecker()
        def checkedParams = paramsChecker.checkParams(request.size() > 0 ? request : ["-h"])
        def latitude = checkedParams.latitude
        def longitude = checkedParams.longitude
        def placeCount = Integer.parseInt(checkedParams.count)
        log.info("Input parameters -->\nlatitude=${latitude}\nlongitude=${longitude}\nplaceCount=${placeCount}")

        try {
            // builds GPA url
            log.info("Building GPA url")
            urlBuilder = new GpaUrlBuilder(latitude, longitude)
            URL completeGpaUrl = urlBuilder.build()

            // receives response object from GPA
            log.info("Receiving response object from GPA")
            def responseObject = jConverter.toObject(dataChecker.getResponseData(completeGpaUrl))
            try {
                Thread.sleep(3000)
                responseObject = jConverter.toObject(dataChecker.getResponseData(completeGpaUrl))
            } catch (Exception ex) {
                log.info("Error wile receiving response object from GPA")
                return new ResultObject("ERROR", "Error wile receiving response object from GPA:\n ${ex.getMessage()}")
            }

            // gets the result object from GPA response object
            log.info("Getting the object from GPA response object")
            ResultObject resultObject = dataParser.parse(responseObject)

            // checks places
            log.info("Checking places")
            CheckingService checkingService = new CheckingService(resultObject, latitude, longitude, placeCount, responseObject.next_page_token ?: null)
            def result = checkingService.check(resultObject)
            log.info("Converting result object to JSON")
            return jConverter.toJson(result)
        } catch (Exception e) {
            log.info("Error while searching places")
            return new ResultObject("ERROR", "Error while searching places:\n ${e.getMessage()}")
        }
    }
}
