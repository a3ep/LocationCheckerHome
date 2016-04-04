package net.bondar.impl

import net.bondar.interfaces.*
import net.bondar.models.Place
import net.bondar.models.ResultObject

/**
 * Sets distance between two coordinates into place object
 */
class PlaceDistanceSetter implements DistanceSetter{
    private ResultObject result
    private String latitude
    private String longitude
    private DistanceCalculator dCalculator

    PlaceDistanceSetter(ResultObject result, def latitude, def longitude, DistanceCalculator calculator){
        this.result = result
        this.latitude = latitude
        this.longitude = longitude
        this.dCalculator = calculator
    }

    @Override
    def setDistance(def count) {
        List<Place> places = new ArrayList<>()
        for(int i=0; i<count; i++){
            Place place = result.places.get(i)
            def distance = dCalculator.calculateDistance(latitude, longitude, place.latitude, place.longitude)
            place.setDistance(String.format("%.1f", distance)+"м")
            places.add(place)
        }
        result.places = places
        return result
    }
}
