package net.bondar.services

import groovy.util.logging.Log4j
import net.bondar.exceptions.LocationCheckerException
import net.bondar.impl.CalculationIterator
import net.bondar.impl.GPSDistanceCalculator
import net.bondar.interfaces.*
import net.bondar.models.InputObject

/**
 * Processes creation of temp result object.
 */
@Log4j
class APIProcessor implements Processor {
    private InputObject object = new InputObject()
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
     * Processes creation of input object.
     *
     * @return complete input object
     * @throws LocationCheckerException
     */
    @Override
    InputObject process() {
        log.info("Processes places")
        try {
            while (iterator.hasNext()) {
                object.places.addAll(iterator.next())
                if (object.places.size() == 0) {
                    return new InputObject("OK", "We could not find any place on the specified coordinates. GPA response status --> ZERO_RESULTS")
                }
            }
            object.status = "OK"
            object.places = object.places.subList(0, placeCount)
            setDistance(object)
        } catch (LocationCheckerException e) {
            new InputObject(e.message)
        }
    }

    /**
     * Adds distance between the specified coordinates and current place coordinates to place object.
     *
     * @param object temp result object with a list of places in which needs to add distances
     * @return updated temp result object
     */
    def setDistance = { obj ->
        obj.places.each {
            def dist = distanceCalculator.calculateDistance(latitude, longitude, it.latitude, it.longitude)
            def formatDist = String.format("%.1f", dist) + "м"
            it.setDistance(formatDist)
        }
        return obj
    }
}
