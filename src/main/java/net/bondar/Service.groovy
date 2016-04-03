package net.bondar

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import net.bondar.impl.*
import net.bondar.interfaces.*
import net.bondar.models.ResultObject

class Service {
    private UrlBuilder urlBuilder
    private DataChecker dataChecker
    private DataParser dataParser
    private Filter filter
    private JsonConverterInt jConverter

    Service(){}

    Service(UrlBuilder urlBuilder, DataChecker dataChecker, DataParser dataParser, JsonConverterInt jConverter){
        this.urlBuilder = urlBuilder
        this.dataChecker = dataChecker
        this.dataParser = dataParser
        this.jConverter = jConverter
    }
//________*****MAIN METHOD********_______________________________________________________
    public static void main(String[] args) {
        def resultJson
        def latitude="48.5086468"
        def longitude="34.988425"
        def placeCount = 2
        /*Checking input params*/
        ParamsChecker paramsChecker = new ParamsChecker()
        def checkedParams = paramsChecker.checkParams(args.size()>0?args:["-help"])

        Service service = new Service(new GpaUrlBuilder(latitude, longitude), new GpaDataChecker(), new GpaDataParser(), new GroovyJsonConverter(new JsonBuilder(), new JsonSlurper()))
        /* Builds GPA url*/
        URL completeGpaUrl = service.urlBuilder.build()

        /*Receiving response object from GPA*/
        def responseObject
        try{
            responseObject = service.jConverter.toObject(service.dataChecker.getResponseData(completeGpaUrl))
        }
        catch (Exception e){
            try{
                Thread.sleep(3000)
                responseObject=service.jConverter.toObject(service.dataChecker.getResponseData(completeGpaUrl))
            }
            catch (Exception ex){
                println ex.getMessage()
            }
        }
        println(responseObject)

        /*Getting the list of places from GPA response object*/
        ResultObject resultObject = service.dataParser.parse(responseObject)
        /*Checking places*/
        CheckingService checkingService = new CheckingService(resultObject, latitude, longitude, placeCount, responseObject.next_page_token?:null)
        def result = checkingService.check(resultObject)
        resultJson = service.jConverter.toJson(result)
    }
}
