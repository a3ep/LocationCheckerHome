package net.bondar

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import net.bondar.impl.*
import net.bondar.interfaces.DataChecker
import net.bondar.interfaces.DataParser
import net.bondar.interfaces.DistanceSetter
import net.bondar.interfaces.JsonConverterInt
import net.bondar.models.Place
import net.bondar.models.ResultObject

class CheckingService {
    private ResultObject result
    private String latitude
    private String longitude
    private def placeCount
    private def count= new Integer(placeCount)
    private def nextPage
    private DistanceSetter distanceSetter = new PlaceDistanceSetter(result, latitude, longitude, new PlaceDistanceCalculator())
    private DataChecker dataChecker = new GpaDataChecker()
    private JsonConverterInt jConverter = new GroovyJsonConverter(new JsonBuilder(), new JsonSlurper())
    private DataParser dataParser = new GpaDataParser()
    private ArrayList<Place> resultPlaceList = new ArrayList<>()

    CheckingService(ResultObject result, String latitude, String longitude, placeCount, def nextPage) {
        this.result = result
        this.latitude = latitude
        this.longitude = longitude
        this.placeCount = placeCount
        this.nextPage = nextPage
    }

    ResultObject check(ResultObject result){
        if(placeCount>result.places.size()){
            placeCount-=result.places.size()
            ResultObject checkedObject = distanceSetter.setDistance(result.places.size())
            resultPlaceList.addAll(checkedObject.places)
            println resultPlaceList.size()
            def newObject = jConverter.toObject(dataChecker.getResponseData(new GpaUrlBuilder(nextPage).build()))
            if(newObject.next_page_token) nextPage = newObject.next_page_token
            checkedObject = check((ResultObject)dataParser.parse(newObject))
            if(resultPlaceList.size()!=count) resultPlaceList.addAll(checkedObject.places)
            if(checkedObject.places.size()!=resultPlaceList.size())checkedObject.places = resultPlaceList
            checkedObject.places.sort {a,b ->
                a.distance<=>b.distance
            }
//            println checkedObject.places
            return checkedObject
        }
        return distanceSetter.setDistance(placeCount)
    }

}
