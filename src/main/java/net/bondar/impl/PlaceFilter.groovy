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
    private DistanceCalculator dCalculator

    PlaceFilter(ResultObject result, def latitude, def longitude, DistanceCalculator calculator){
        this.result = result
        this.latitude = latitude
        this.longitude = longitude
        this.dCalculator = calculator
    }

    @Override
    def doFilter(def count) {
        List<Place> places = new ArrayList<>()
        for(int i=0; i<count; i++){
            Place place = result.places.get(i)
            place.setDistance(dCalculator.calculateDistance(latitude, longitude, place.latitude, place.longitude))
            places.add(place)
        }
        result.places = places
        return result
    }
}
