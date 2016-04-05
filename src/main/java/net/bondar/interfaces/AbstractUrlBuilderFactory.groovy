package net.bondar.interfaces
/**
 * Creates concrete URL builders.
 */
interface AbstractUrlBuilderFactory {

    /**
     * Creates concrete URL builders on the basis of received arguments.
     *
     * @param latitude the specified latitude
     * @param longitude the specified longitude
     * @param pageToken next_page_token from Google Places API response
     * @return URL builder
     */
    UrlBuilder createUrlBuilder(String latitude, String longitude, String pageToken)
}
