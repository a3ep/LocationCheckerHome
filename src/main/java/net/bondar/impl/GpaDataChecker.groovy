package net.bondar.impl

import groovy.util.logging.Log
import net.bondar.interfaces.DataChecker

/**
 * Opens http-connection and receives response data
 */
@Log
class GpaDataChecker implements DataChecker{
/**
 * Opens http-connection and receives response data
 * @param url - url for connection
 * @return response data
 */
    def getResponseData(def url){
        if(url.toString().contains("pagetoken")) Thread.sleep(2000)
        def connect = (HttpURLConnection) url.openConnection()
        def responseCode = connect.getResponseCode()
        if(responseCode != 200){
            log.info("Error while getting response from GPA")
            throw new Exception("Error while getting response from GPA.\nWrong responce code:  ${responseCode}")
        }
        return new InputStreamReader(connect.getInputStream())
    }
}
