package net.bondar.impl

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import net.bondar.interfaces.*
import net.bondar.models.Place
import net.bondar.models.ResultObject

class PlaceFilter implements Filter{
    private ResultObject result
    private String latitude
    private String longitude
    private def minDistance
    private DistanceCalculator dCalculator
    private UrlBuilder urlBuilder
    private JSONConverter jConverter= new JSONConverter(new JsonBuilder(), new JsonSlurper())
    private DataChecker dataChecker
    private DataParser dataParser

    PlaceFilter(ResultObject result, def latitude, def longitude, def minDistance, DistanceCalculator calculator, DataChecker dataChecker, DataParser dataParser){
        this.result = result
        this.latitude = latitude
        this.longitude = longitude
        this.minDistance = minDistance
        this.dCalculator = calculator
        this.dataChecker = dataChecker
        this.dataParser = dataParser
    }

    @Override
    def doFilter() {
        def number=0
        if (result.places.size()==1){
            println result
        }
        String targetLat = result.places.get(0).latitude
        String targetLng = result.places.get(0).longitude
        double targetMinDist = dCalculator.calculateDistance(latitude, longitude, targetLat, targetLng)
        ArrayList<Place> sameLocations = new ArrayList()
        ArrayList<Place> closestLocation = new ArrayList()

        for (int i = 1; i < result.places.size(); i++) {
            String currentLat = result.places.get(i).latitude
            String currentLng = result.places.get(i).longitude
            double targetDistance = dCalculator.calculateDistance(latitude, longitude, currentLat, currentLng)
            if(targetDistance<=>0d==0){
                sameLocations.add(result.places.get(i))
                continue
            }
            if (targetDistance <=> targetMinDist == -1) {
                targetMinDist = targetDistance
                number=i
            }
        }
        if(/*result.places.size()<20 && */sameLocations.size()!=0){
            result.places=sameLocations
            return result
        }
//        if(result.places.size()<20){
            closestLocation.add(result.places.remove(number))
            result.places.each{
                if(dCalculator.calculateDistance(latitude, longitude, it.latitude, it.longitude)<=> targetMinDist == 0){
                    closestLocation.add(it)
                }
            }
            result.places=closestLocation
            return result
//        }
//        minDistance=targetMinDist
//        urlBuilder = new GpaUrlBuilder(latitude, longitude, minDistance)
//        def responseObject = jConverter.toObject(dataChecker.getResponseData(urlBuilder.buildURL()))
//        result = dataParser.parse(responseObject)
//        result = doFilter()
//        return result
    }
}
