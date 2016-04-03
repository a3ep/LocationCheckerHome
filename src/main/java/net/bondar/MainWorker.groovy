package net.bondar

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import net.bondar.interfaces.DataChecker
import net.bondar.interfaces.DataParser
import net.bondar.interfaces.Filter
import net.bondar.interfaces.JsonConverterInt
import net.bondar.interfaces.UrlBuilder

import net.bondar.impl.*
import net.bondar.models.ResultObject

class MainWorker {
    private UrlBuilder urlBuilder
    private DataChecker dataChecker
    private DataParser dataParser
    private Filter filter
    private JsonConverterInt jConverter

    MainWorker(){}
    MainWorker(UrlBuilder urlBuilder, DataChecker dataChecker, DataParser dataParser, JsonConverterInt jConverter){
        //ConfigLoader loader = new ConfigLoader(CONFIG_FILE)
        this.urlBuilder = urlBuilder
        this.dataChecker = dataChecker
        this.dataParser = dataParser
        this.jConverter = jConverter
    }
//________*****MAIN METHOD********_______________________________________________________
    public static void main(String[] args) {
        def resultJson
        def latitude="48.4644455"
        def longitude="35.0475775"
        /*Checking input params*/



//        /*Reading LATITUDE and LONGITUDE from console*/
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
//        try{
//            println("Set latitude:")
//            latitude = reader.readLine()
//            println("Set longitude:")
//            longitude =reader.readLine()
//        }
//        catch (Exception e){
//            e.printStackTrace()
//        }finally{
//            reader.close()
//        }
        MainWorker worker = new MainWorker(new GpaUrlBuilder(latitude, longitude, "30"), new GpaDataChecker(), new GpaDataParser(), new JSONConverter(new JsonBuilder(), new JsonSlurper()))
        /* Builds GPA url*/
        URL completeGpaUrl = worker.urlBuilder.buildURL()

        /*Receiving response object from GPA*/
        def responseObject
        try{
            responseObject = worker.jConverter.toObject(worker.dataChecker.getResponseData(completeGpaUrl))
        }
        catch (Exception e){
            try{
                Thread.sleep(3000)
                responseObject=worker.jConverter.toObject(worker.dataChecker.getResponseData(completeGpaUrl))
            }
            catch (Exception ex){
                println ex.getMessage()
            }
        }

        println(responseObject)
        println(responseObject.status)

        /*Checking response status*/
//        if(!responseObject.status == 'OK' || !responseObject.status == 'ZERO_RESULTS')
//            throw new LocationCheckerException("Error while searching places --> ${responseObject.status}");
//        if(responseObject.status == 'ZERO_RESULTS'){
//            jsonResult = worker.jConverter.toJson(new ResultObject(responseObject.status, "We could not find any place on the specified coordinates"))
//        }

        /*Getting the list of places from GPA response object*/
        ResultObject resultObject = worker.dataParser.parse(responseObject)
        worker.filter = new PlaceFilter(resultObject, latitude, longitude, "30", new PlaceDistanceCalculator(), worker.dataChecker, worker.dataParser)
        def result = worker.filter.doFilter()
        resultJson = worker.jConverter.toJson(result)
    }
}
