package net.bondar.impl

import groovy.util.logging.Log4j

/**
 * Builds Google Places API URL on the basis of latitude and longitude.
 */
@Log4j
class CoordinatesGPAUrlBuilder extends AbstractGPAUrlBuilder {
    ParameterLoader parameterLoader = new ParameterLoader("config.groovy")
    private String types = parameterLoader.loadParameter("placeTypes")
    private placeTypes = types.substring(1, types.length() - 2).replace(', ', "|")
    private String latitude
    private String longitude


    CoordinatesGPAUrlBuilder(String latitude, String longitude) {
        this.latitude = latitude
        this.longitude = longitude
    }

    /**
     * Builds Google Places API URL on the basis of latitude and longitude.
     *
     * @return Google Places API URL
     */
    @Override
    URL build() {
        log.info("Builds Google Places URL on the basis of geographical coordinates\n")
        StringBuilder urlBuilder = new StringBuilder(super.mainUrlPart).append("location=${latitude},${longitude}").append("&rankby=distance").append("&types=${placeTypes}").append("&key=${super.gpaKey}")
        new URL(urlBuilder.toString())
    }
}
