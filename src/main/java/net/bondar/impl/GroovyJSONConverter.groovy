package net.bondar.impl

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Log4j
import net.bondar.exceptions.ApplicationException
import net.bondar.interfaces.JSONConverter
import org.apache.log4j.Level

/**
 * Converts JSON to Object / Object to JSON.
 */
@Log4j
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
     * @param json JSON string that needs to convert
     * @return converted object
     * @throws ApplicationException
     */
    Object toObject(InputStreamReader json) {
        try {
            jSlurper.parse(json)
        }
        catch (Exception e) {
            log.setLevel(Level.DEBUG)
            log.debug("Error while reading data from Google Places API response: \\n${e.getMessage()}")
            throw new ApplicationException("Error while reading data from Google Places API response: \n${e.getMessage()}")
        }
    }

    /**
     * Converts Object to JSON.
     *
     * @param object object that needs to convert
     * @return converted JSON
     * @throws ApplicationException
     */
    Object toJson(Object object) {
        try {
            jBuilder.call(object)
        }
        catch (Exception e) {
            log.setLevel(Level.DEBUG)
            log.debug("Error while building JSON: \n${e.getMessage()}")
            throw new ApplicationException("Error while building JSON: \n${e.getMessage()}")
        }
    }
}
