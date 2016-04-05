package net.bondar.impl

import groovy.util.logging.Log

/**
 * Builds Google Places API URL on the basis of latitude and longitude.
 */
@Log
class CoordinatesGPAUrlBuilder extends AbstractGPAUrlBuilder {
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
        log.info("Builds Google Places URL on the basis of geographical coordinates")
        StringBuilder urlBuilder = new StringBuilder(super.mainUrlPart).append("location=${latitude},${longitude}").append("&rankby=distance").append("&types=${super.placeTypes}").append("&key=${super.gpaKey}")
        new URL(urlBuilder.toString())
    }
}
