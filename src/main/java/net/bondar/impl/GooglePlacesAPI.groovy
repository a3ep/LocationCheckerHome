package net.bondar.impl

import groovy.util.logging.Log4j
import net.bondar.exceptions.ApplicationException
import net.bondar.interfaces.APIConnection
import org.apache.log4j.Level

/**
 * Connects and receives response data from Google Places API.
 */
@Log4j
class GooglePlacesAPI implements APIConnection {

    /**
     * Connects to Google Places API, checks response code and gives back InputStreamReader.
     *
     * @param url query to Google Places API
     * @return Google Places API response in format InputStreamReader
     * @throws ApplicationException
     */
    InputStreamReader getInputStream(URL url) {
        log.info("Receives response object from Google Places API\n")
        def connect = (HttpURLConnection) url.openConnection()
        def responseCode = connect.getResponseCode()
        if (responseCode != 200) {
            log.setLevel(Level.DEBUG)
            log.debug("Error while getting response from GPA")
            throw new ApplicationException("Error while getting response from GPA.\nWrong responce code:  ${responseCode}")
        }
        new InputStreamReader(connect.getInputStream())
    }
}
