package net.bondar.impl

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import net.bondar.exceptions.ApplicationException
import net.bondar.interfaces.JSONConverter

/**
 * Converts JSON to Object / Object to JSON.
 */
class GroovyJSONConverter implements JSONConverter {
    private final JsonBuilder jBuilder
    private final JsonSlurper jSlurper

    GroovyJSONConverter(JsonBuilder builder, JsonSlurper slurper) {
        this.jBuilder = builder
        this.jSlurper = slurper
    }

    /**
     * Converts JSON to Object.
     *
     * @param json json string that needs to convert
     * @return converted object
     */
    Object toObject(InputStreamReader json) {
        try {
            jSlurper.parse(json)
        }
        catch (Exception e) {
            throw new ApplicationException("Error while reading data from Google Places API response: \n${e.getMessage()}")
        }
    }

    /**
     * Converts Object to JSON.
     *
     * @param object object that needs to convert
     * @return converted JSON
     */
    Object toJson(Object object) {
        try {
            jBuilder.call(object)
        }
        catch (Exception e) {
            throw new ApplicationException("Error while building JSON: \n${e.getMessage()}")
        }
    }

}
