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
class MainService {
    private UrlBuilder urlBuilder
    private DataChecker dataChecker
    private DataParser dataParser
    private JsonConverterInt jConverter
    private ParamsChecker paramsChecker

    MainService(){
        this.dataChecker = new GpaDataChecker()
        this.dataParser = new GpaDataParser()
        this.jConverter = new GroovyJsonConverter(new JsonBuilder(), new JsonSlurper())
        this.paramsChecker = new ParamsChecker()
    }
    /**
     * Method work with user requests
     * @param args - user's request
     * @return result object with places or with error message
     */

    def search(def args){
        def resultJson
        /*Checking input params*/
        log.info("Checking input params\n")
        ParamsChecker paramsChecker = new ParamsChecker()
        def checkedParams = paramsChecker.checkParams(args.size()>0?args:["-h"])
        def latitude = checkedParams.latitude
        def longitude = checkedParams.longitude
        def placeCount = Integer.parseInt(checkedParams.count)
        try{
            /* Builds GPA url*/
            log.info("Building GPA url")
            urlBuilder = new GpaUrlBuilder(latitude, longitude)
            URL completeGpaUrl = urlBuilder.build()
            /*Receiving response object from GPA*/
            log.info("Receiving response object from GPA")
            def responseObject
            responseObject = jConverter.toObject(dataChecker.getResponseData(completeGpaUrl))
            try{
                Thread.sleep(3000)
                responseObject=jConverter.toObject(dataChecker.getResponseData(completeGpaUrl))
            }
            catch (Exception ex){
                log.info("Error wile receiving response object from GPA")
                return new ResultObject("ERROR", "Error wile receiving response object from GPA:\n ${ex.getMessage()}")
            }
            /*Getting the result object from GPA response object*/
            log.info("Getting the object from GPA response object")
            ResultObject resultObject = dataParser.parse(responseObject)
            /*Checking places*/
            log.info("Checking places")
            CheckingService checkingService = new CheckingService(resultObject, latitude, longitude, placeCount, responseObject.next_page_token?:null)
            def result = checkingService.check(resultObject)
            log.info("Converting result object to JSON")
            resultJson = jConverter.toJson(result)
            return resultJson
        }
        catch (Exception e){
            log.info("Error while searching places")
            return new ResultObject("ERROR", "Error while searching places:\n ${e.getMessage()}")
        }
    }
}
