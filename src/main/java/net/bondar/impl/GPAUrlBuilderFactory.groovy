package net.bondar.impl

import net.bondar.interfaces.AbstractUrlBuilderFactory
import net.bondar.interfaces.UrlBuilder

/**
 *
 */
class GPAUrlBuilderFactory implements AbstractUrlBuilderFactory {
    /**
     *
     *
     * @param latitude
     * @param longitude
     * @param pageNumber
     * @return
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
