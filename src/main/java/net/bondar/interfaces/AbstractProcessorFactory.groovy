package net.bondar.interfaces
/**
 * Creates concrete API processors.
 */
interface AbstractProcessorFactory {

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
    Processor createProcessor(String latitude, String longitude, int placeCount, APIConnection apiConnection, ObjectChecker objectChecker, JSONConverter jsonConverter)
}