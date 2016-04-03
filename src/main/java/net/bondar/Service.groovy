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
        def placeCount
        /*Checking input params*/

        Service service = new Service(new GpaUrlBuilder(latitude, longitude), new GpaDataChecker(), new GpaDataParser(), new JSONConverter(new JsonBuilder(), new JsonSlurper()))
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
        ArrayList list = new ArrayList()
        PlaceDistanceCalculator calculator = new PlaceDistanceCalculator()
        resultObject.places.each {
            list.add(calculator.calculateDistance(latitude, longitude, it.latitude, it.longitude))
        }
        println list
        service.filter = new PlaceFilter(resultObject, latitude, longitude, new PlaceDistanceCalculator())
        def result = service.filter.doFilter()
        resultJson = service.jConverter.toJson(result)
    }
}
