package net.bondar.services

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import net.bondar.impl.GPSDistanceCalculator
import net.bondar.impl.GooglePlacesAPI
import net.bondar.impl.GroovyJSONConverter
import net.bondar.impl.PlaceIterator
import net.bondar.interfaces.APIConnection
import net.bondar.interfaces.DistanceCalculator
import net.bondar.interfaces.JSONConverter
import net.bondar.models.PlaceKeeper
import net.bondar.models.TempResultObject

/**
 * Processes
 */
class ProcessingService {
    private TempResultObject object = new TempResultObject("OK")
    private String latitude
    private String longitude
    private int placeCount
    private DistanceCalculator distanceCalculator = new GPSDistanceCalculator()
    private APIConnection apiConnection = new GooglePlacesAPI()
    private JSONConverter jsonConverter = new GroovyJSONConverter(new JsonBuilder(), new JsonSlurper())
    private Iterator iterator = new PlaceIterator(latitude, longitude, placeCount)

    ProcessingService(String latitude, String longitude, int placeCount) {
        this.latitude = latitude
        this.longitude = longitude
        this.placeCount = placeCount
    }
    /**
     *
     * @return
     */
    TempResultObject process() {
        while (iterator.hasNext()) {
            object.places.addAll(((PlaceKeeper) iterator.next()).places)
        }
        object.places = object.places.subList(0, placeCount)
        setDistanceToPlaces(object)
    }

    TempResultObject setDistanceToPlaces(TempResultObject object) {
        object.places.each {
            it.distance = distanceCalculator.calculateDistance(latitude, longitude, it.latitude, it.longitude)
        }
        return object
    }
}
