package net.bondar.impl

import net.bondar.interfaces.*
import net.bondar.services.APIProcessor

/**
 * Creates concrete API processors.
 */
class APIProcessorFactory implements AbstractProcessorFactory {

    /**
     * Creates concrete API processor on the basis of received arguments.
     *
     * @param latitude the specified latitude
     * @param longitude the specified longitude
     * @param placeCount the specified count of places
     * @param apiConnection concrete api connection
     * @param objectChecker concrete object checker
     * @param jsonConverter concrete json converter
     * @return API processor
     */
    @Override
    Processor createProcessor(String latitude, String longitude, int placeCount, APIConnection apiConnection, ObjectChecker objectChecker, JSONConverter jsonConverter) {
        return new APIProcessor(latitude, longitude, placeCount, apiConnection, objectChecker, jsonConverter)
    }


}
