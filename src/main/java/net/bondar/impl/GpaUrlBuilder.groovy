package net.bondar.impl

import net.bondar.exceptions.LocationCheckerException
import net.bondar.interfaces.UrlBuilder

/**
 * Class for building URL
 */
class GpaUrlBuilder implements UrlBuilder{
    private final String MAIN_URL_PART
    private final String GPA_KEY
    private final String PLACE_TYPES
    private def latitude
    private def longitude
    private def pageToken

    GpaUrlBuilder(def latitude, def longitude){
        this.latitude = latitude
        this.longitude = longitude
        ConfigLoader loader
        try {
            loader = new ConfigLoader("config.groovy")
        }
        catch (Exception e){
            throw  new LocationCheckerException("Error while load configuration: \n${e.getMessage()}")
        }
        MAIN_URL_PART = loader.loadParameter("placeSearchRequest")
        GPA_KEY = loader.loadParameter("gpaKey")
        PLACE_TYPES = loader.loadParameter("placeTypes")
    }
    GpaUrlBuilder(def pageToken){
        this.pageToken = pageToken
        ConfigLoader loader
        try {
            loader = new ConfigLoader("config.groovy")
        }
        catch (Exception e){
            throw  new LocationCheckerException("Error while load configuration: \n${e.getMessage()}")
        }
        MAIN_URL_PART = loader.loadParameter("placeSearchRequest")
        GPA_KEY = loader.loadParameter("gpaKey")
    }
    @Override
    def  build(){
        if(pageToken) return buildURL(pageToken)
        return buildURL(latitude, longitude)
    }
    /**
     * Builds GPA url
     * @param latitude - specified LATITUDE
     * @param longitude - specified LONGITUDE
     * @param radius - search radius
     * @return GPA url
     */
    def buildURL(def latitude, def longitude){
        StringBuilder urlBuilder = new StringBuilder(MAIN_URL_PART)
        urlBuilder.append("location=" + latitude + "," + longitude)
        urlBuilder.append("&rankby=distance")
        urlBuilder.append("&types="+ PLACE_TYPES)
        urlBuilder.append("&key=" + GPA_KEY)
        println urlBuilder.toString()
        return new URL(urlBuilder.toString())
    }

    def buildURL(def pageToken){
        StringBuilder urlBuilder = new StringBuilder(MAIN_URL_PART)
        urlBuilder.append("pagetoken="+pageToken)
        urlBuilder.append("&key=" + GPA_KEY)
        println urlBuilder.toString()
        return new URL(urlBuilder.toString())
    }


}
