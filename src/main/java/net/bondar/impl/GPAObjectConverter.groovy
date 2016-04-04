package net.bondar.impl

import groovy.util.logging.Log
import net.bondar.exceptions.LocationCheckerException
import net.bondar.interfaces.ObjectConverter
import net.bondar.models.Place
import net.bondar.models.TempResultObject

/**
 * Converts json object with location information from Google Places API to TempResultObject
 */
@Log
class GPAObjectConverter implements ObjectConverter {
    /**
     * Convert json object to TempResultObject.
     *
     * @param responseObject location information from Google Places API
     * @return empty temp object with comment if could not finds any places on the specified coordinates
     * or temp object with found places
     */
    TempResultObject convert(Object responseObject) {
        if (responseObject.status == 'OK' || responseObject.status == 'ZERO_RESULTS') {
            if (responseObject.status == 'ZERO_RESULTS') {
                new TempResultObject("OK", "We could not find any place on the specified coordinates. GPA response status --> ${responseObject.status}")
            }
            ArrayList<Place> placeList = new ArrayList<>()
            responseObject.results.each {
                placeList.add(new Place("${it.name.value}", "${it.geometry.location.lat}", "${it.geometry.location.lng}"))
            }
            TempResultObject tmpObject = new TempResultObject("OK")
            tmpObject.setPlaces(placeList)
        } else {
            log.info("Error while checking GPA response status.")
            throw new LocationCheckerException("Error while checking GPA response status.\nResponce status --> ${responseObject.status}");
        }
    }
}
