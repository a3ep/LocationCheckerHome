package net.bondar.impl

import net.bondar.interfaces.UrlBuilder

/**
 * Class for building URL
 */
class GpaUrlBuilder implements UrlBuilder{
    ConfigLoader loader = new ConfigLoader("config.groovy")
    private final String MAIN_URL_PART = loader.loadParameter("placeSearchRequest")
    private final String GPA_KEY = loader.loadParameter("gpaKey")
    private final String PLACE_TYPES = loader.loadParameter("placeTypes")
    private String latitude
    private String longitude
    private String radius

    GpaUrlBuilder(){}
    GpaUrlBuilder(def latitude, def longitude, def radius){
        this.latitude = latitude
        this.longitude = longitude
        this.radius = radius
    }

    @Override
    URL buildURL() {
        return buildURL(latitude, longitude, radius)
    }

    /**
     * Builds GPA url
     * @param latitude - specified LATITUDE
     * @param longitude - specified LONGITUDE
     * @param radius - search radius
     * @return GPA url
     */
    def buildURL(def latitude, def longitude, def radius){
        StringBuilder urlBuilder = new StringBuilder(MAIN_URL_PART)
        urlBuilder.append("location=" + latitude + "," + longitude)
//        urlBuilder.append("&radius=" + radius)
        urlBuilder.append("&rankby=distance")
        urlBuilder.append("&types="+ PLACE_TYPES)
        urlBuilder.append("&key=" + GPA_KEY)
        println urlBuilder.toString()
        return new URL(urlBuilder.toString())
    }


}
