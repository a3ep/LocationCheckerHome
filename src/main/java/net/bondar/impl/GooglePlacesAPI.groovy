package net.bondar.impl

import groovy.util.logging.Log
import net.bondar.exceptions.LocationCheckerException
import net.bondar.interfaces.APIConnection

/**
 * Opens http-connection and receives response data from Google Places API
 */
@Log
class GooglePlacesAPI implements APIConnection {
/**
 * Connects to WWO, checking response code and giving back InputStreamReader.
 *
 * @param url query to Google Places API
 * @return InputStreamReader for Json parser
 */
    InputStreamReader getInputStream(URL url) {
        if (url.toString().contains("pagetoken")) {
            Thread.sleep(2000)
        }
        def connect = (HttpURLConnection) url.openConnection()
        def responseCode = connect.getResponseCode()
        if (responseCode != 200) {
            log.info("Error while getting response from GPA")
            throw new LocationCheckerException("Error while getting response from GPA.\nWrong responce code:  ${responseCode}")
        }
        new InputStreamReader(connect.getInputStream())
    }
}
