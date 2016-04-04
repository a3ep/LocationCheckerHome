package net.bondar.impl

import net.bondar.exceptions.LocationCheckerException
import net.bondar.interfaces.DataParser
import net.bondar.models.Place
import net.bondar.models.ResultObject

class GpaDataParser implements DataParser{
    /**
     * Receiving list of places from response object
     * @param responseObject - location information from GPAPI
     * @return list of closest places
     */
    ResultObject parse(def responseObject){
        if(responseObject.status == 'OK' || responseObject.status == 'ZERO_RESULTS'){
            if(responseObject.status == 'ZERO_RESULTS'){
                return new ResultObject("OK", "We could not find any place on the specified coordinates. GPA response status --> ${responseObject.status}")
            }
            ResultObject result = new ResultObject("OK")
            responseObject.results.each{
                result.places.add(new Place("${it.name.value}", "${it.geometry.location.lat}", "${it.geometry.location.lng}"))
            }
            println("Closest places:\n"+result.places+"\n")
            return result
        }
        throw new LocationCheckerException("Error while searching places. GPA responce status --> ${responseObject.status}");

    }
}
