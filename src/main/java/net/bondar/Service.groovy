package net.bondar

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import net.bondar.impl.*
import net.bondar.interfaces.*
import net.bondar.models.ResultObject

/**
 * Main service which work with user requests and passing tasks to another classes
 */
class Service {
    private UrlBuilder urlBuilder
    private DataChecker dataChecker
    private DataParser dataParser
    private JsonConverterInt jConverter
    private ParamsChecker paramsChecker

    Service(){
        this.dataChecker = new GpaDataChecker()
        this.dataParser = new GpaDataParser()
        this.jConverter = new GroovyJsonConverter(new JsonBuilder(), new JsonSlurper())
        this.paramsChecker = new ParamsChecker()
    }

    def search(def args){
        def resultJson
        def latitude
        def longitude
        def placeCount
        /*Checking input params*/
//        def args = ["-param", "${lat},${lng},${count}"]
        ParamsChecker paramsChecker = new ParamsChecker()
        def checkedParams = paramsChecker.checkParams(args.size()>0?args:["-help"])
        latitude = checkedParams.latitude
        longitude = checkedParams.longitude
        placeCount = Integer.parseInt(checkedParams.count)

        Service service = new Service()
        /* Builds GPA url*/
        try{
            service.urlBuilder = new GpaUrlBuilder(latitude, longitude)
            URL completeGpaUrl = service.urlBuilder.build()
            /*Receiving response object from GPA*/
            def responseObject
            responseObject = service.jConverter.toObject(service.dataChecker.getResponseData(completeGpaUrl))
            try{
                Thread.sleep(3000)
                responseObject=service.jConverter.toObject(service.dataChecker.getResponseData(completeGpaUrl))
            }
            catch (Exception ex){
                return new ResultObject("ERROR", ex.getMessage())
            }
            /*Getting the list of places from GPA response object*/
            ResultObject resultObject = service.dataParser.parse(responseObject)
            /*Checking places*/
            CheckingService checkingService = new CheckingService(resultObject, latitude, longitude, placeCount, responseObject.next_page_token?:null)
            def result = checkingService.check(resultObject)
            resultJson = service.jConverter.toJson(result)
        }
        catch (Exception e){
            return new ResultObject("ERROR", e.getMessage())
        }
    }
}
