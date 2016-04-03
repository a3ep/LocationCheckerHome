package net.bondar.impl

import net.bondar.interfaces.DataChecker

/**
 * Opens http-connection and receives response data
 */
class GpaDataChecker implements DataChecker{
/**
 * Opens http-connection and receives response data
 * @param url - url for connection
 * @return response data
 */
    def getResponseData(def url){
        def connect = (HttpURLConnection) url.openConnection()
        def responseCode = connect.getResponseCode()
        if(responseCode != 200){
            throw new Exception("Wrong responce code:  ${responseCode}")
        }
        return new InputStreamReader(connect.getInputStream())
    }
}
