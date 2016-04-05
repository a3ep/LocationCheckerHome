package net.bondar.impl

import net.bondar.interfaces.UrlBuilder

/**
 * Builds URL on the basis of Google Places API Url parameters.
 */
abstract class AbstractGPAUrlBuilder implements UrlBuilder {
    ParameterLoader pLoader = new ParameterLoader("config.groovy")
    private String types = pLoader.loadParameter("placeTypes")
    private final String mainUrlPart = pLoader.loadParameter("placeSearchRequest")
    private final String gpaKey = pLoader.loadParameter("gpaKey")
    private final String placeTypes = types.substring(1, types.length() - 2).replace(', ', "|")

    String getMainUrlPart() {
        return mainUrlPart
    }

    String getGpaKey() {
        return gpaKey
    }

    String getPlaceTypes() {
        return placeTypes
    }

    /**
     * Builds Google Places API URL.
     *
     * @return Google Places API URL
     */
    @Override
    abstract URL build()
}
