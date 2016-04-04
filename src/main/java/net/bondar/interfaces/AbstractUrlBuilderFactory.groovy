package net.bondar.interfaces
/**
 *
 */
interface AbstractUrlBuilderFactory {
    UrlBuilder createUrlBuilder(String latitude, String longitude, String pageToken)
}
