package net.bondar.impl

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import net.bondar.exceptions.LocationCheckerException
import net.bondar.interfaces.JsonConverterInt

/**
 * Class for converting JSON to Object / Object to JSON
 */
class GroovyJsonConverter implements JsonConverterInt{
    private final JsonBuilder jBuilder
    private final JsonSlurper jSlurper

    GroovyJsonConverter(JsonBuilder builder, JsonSlurper slurper){
        this.jBuilder = builder
        this.jSlurper = slurper
    }
    /**
     * Converts response JSON to Object
     * @param json - json object that needs to convert
     * @return converted object
     */
    def toObject(def json){
        try{
            jSlurper.parse(json)
        }
        catch(Exception e){
            throw new LocationCheckerException("Error while reading data from Google Places Api: \n${e.getMessage()}")
        }
    }
    /**
    * Converts Object to result JSON
    * @param object - result that needs to convert
    * @return - converted JSON
    */
    def toJson(def object){
       try{
           def json = jBuilder.call(object)
           return json
       }
       catch (Exception e){
           throw new LocationCheckerException("Error while building JSON: \n${e.getMessage()}")
       }
    }

}
