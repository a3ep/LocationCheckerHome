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
        if(!responseObject.status == 'OK' || !responseObject.status == 'ZERO_RESULTS')
            throw new LocationCheckerException("Error while searching places --> ${responseObject.status}");
        if(responseObject.status == 'ZERO_RESULTS'){
            return new ResultObject(responseObject.status, "We could not find any place on the specified coordinates")
        }
        ResultObject result = new ResultObject(responseObject.status)
        responseObject.results.each{
            result.places.add(new Place("${it.name.value}", "${it.geometry.location.lat}", "${it.geometry.location.lng}"))
        }
        println("Closest places:\n"+result.places+"\n")
        return result
    }
}
