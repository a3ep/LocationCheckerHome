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
    private def placeCount
    private DistanceCalculator dCalculator

    PlaceFilter(ResultObject result, def latitude, def longitude, def placeCount, DistanceCalculator calculator){
        this.result = result
        this.latitude = latitude
        this.longitude = longitude
        this.placeCount = placeCount
        this.dCalculator = calculator
    }

    @Override
    def doFilter() {
        if (placeCount==1){

            return result
        }
        double targetMinDist = dCalculator.calculateDistance(latitude, longitude, result.places.get(0).latitude, result.places.get(0).longitude)
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
//            if (targetDistance <=> targetMinDist == -1) {
//                targetMinDist = targetDistance
//                number=i
//            }
        }
        if(sameLocations.size()!=0){
            result.places=sameLocations
            return result
        }
        closestLocation.add(result.places.remove(0))
        result.places.each{
            if(dCalculator.calculateDistance(latitude, longitude, it.latitude, it.longitude)<=>targetMinDist == 0){
                closestLocation.add(it)
            }
        }
        result.places=closestLocation
        return result
    }
}
