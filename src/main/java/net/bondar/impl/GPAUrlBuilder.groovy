package net.bondar.impl

import net.bondar.interfaces.UrlBuilder

/**
 * Builds URL on the basis of Google Places API Url parameters.
 */
abstract class GPAUrlBuilder implements UrlBuilder {
    ParameterLoader pLoader = new ParameterLoader("config.groovy")
    private final String mainUrlPart = pLoader.loadParameter("placeSearchRequest")
    private final String gpaKey = pLoader.loadParameter("gpaKey")
    private final String placeTypes = pLoader.loadParameter("placeTypes")

    String getMainUrlPart() {
        return mainUrlPart
    }

    String getGpaKey() {
        return gpaKey
    }

    String getPlaceTypes() {
        return placeTypes
    }

    @Override
    abstract URL build()
    /**
     * Builds GPA url
     * @param latitude - specified latitude
     * @param longitude - specified longitude
     * @param radius - search radius
     * @return GPA url
     */
}
