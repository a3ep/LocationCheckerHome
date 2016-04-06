package net.bondar.impl

import net.bondar.interfaces.UrlBuilder

/**
 * Builds URL on the basis of Google Places API Url parameters.
 */
abstract class AbstractGPAUrlBuilder implements UrlBuilder {
    ParameterLoader pLoader = new ParameterLoader("config.groovy")
    private final String mainUrlPart = pLoader.loadParameter("placeSearchRequest")
    private final String gpaKey = pLoader.loadParameter("gpaKey")

    String getMainUrlPart() {
        return mainUrlPart
    }

    String getGpaKey() {
        return gpaKey
    }

    /**
     * Builds Google Places API URL.
     *
     * @return Google Places API URL
     */
    @Override
    abstract URL build()
}
