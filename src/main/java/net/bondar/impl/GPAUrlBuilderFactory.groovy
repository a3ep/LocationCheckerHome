package net.bondar.impl

import net.bondar.interfaces.AbstractUrlBuilderFactory
import net.bondar.interfaces.UrlBuilder

/**
 * Creates concrete URL builders.
 */
class GPAUrlBuilderFactory implements AbstractUrlBuilderFactory {

    /**
     * Creates concrete URL builders on the basis of received arguments.
     *
     * @param latitude the specified latitude
     * @param longitude the specified longitude
     * @param pageToken next_page_token from Google Places API response
     * @return URL builder
     */
    @Override
    UrlBuilder createUrlBuilder(String latitude, String longitude, String pageToken) {
        if (pageToken) {
            new PageTokenGPAUrlBuilder(pageToken)
        } else {
            new CoordinatesGPAUrlBuilder(latitude, longitude)
        }
    }
}
