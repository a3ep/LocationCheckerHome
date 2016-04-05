package net.bondar.services

import groovy.util.logging.Log
import net.bondar.exceptions.LocationCheckerException
import net.bondar.impl.CalculationIterator
import net.bondar.impl.GPSDistanceCalculator
import net.bondar.interfaces.*
import net.bondar.models.TempResultObject

/**
 * Processes creation of temp result object.
 */
@Log
class APIProcessor implements Processor {
    private TempResultObject object = new TempResultObject("OK")
    private String latitude
    private String longitude
    private int placeCount
    private APIConnection apiConnection
    private ObjectChecker objectChecker
    private JSONConverter jsonConverter
    private DistanceCalculator distanceCalculator = new GPSDistanceCalculator()
    private CalculationIterator iterator = new CalculationIterator(latitude, longitude, placeCount, apiConnection, objectChecker, jsonConverter)

    APIProcessor(String latitude, String longitude, int placeCount, APIConnection apiConnection, ObjectChecker objectChecker, JSONConverter jsonConverter) {
        this.latitude = latitude
        this.longitude = longitude
        this.placeCount = placeCount
        this.apiConnection = apiConnection
        this.objectChecker = objectChecker
        this.jsonConverter = jsonConverter
    }

    /**
     * Processes creation of temp result object.
     *
     * @return complete temp result object
     */
    @Override
    TempResultObject process() {
        log.info("Processes places")
        try {
            while (iterator.hasNext()) {
                object.places.addAll(iterator.updateKeeper().places)
                if (object.places.size() == 0) {
                    new TempResultObject("OK", "We could not find any place on the specified coordinates. GPA response status --> ${responseObject.status}", "")
                }
            }
            object.places = object.places.subList(0, placeCount)
            setDistanceToPlaces(object)
        } catch (LocationCheckerException e) {
            new TempResultObject("ERROR", e.message)
        }
    }

    /**
     * Adds distance between the specified coordinates and current place coordinates to place object.
     *
     * @param object temp result object with a list of places in which needs to add distances
     * @return updated temp result object
     */
    TempResultObject setDistanceToPlaces(TempResultObject object) {
        object.places.each {
            it.distance = distanceCalculator.calculateDistance(latitude, longitude, it.latitude, it.longitude)
        }
        return object
    }
}
