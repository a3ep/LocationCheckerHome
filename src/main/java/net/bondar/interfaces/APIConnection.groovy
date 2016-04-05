package net.bondar.interfaces
/**
 * Connects and receives response data.
 */
interface APIConnection {

    /**
     * Connects to API, checks response code and gives back InputStreamReader.
     *
     * @param url query to PI
     * @return API response in format InputStreamReader
     */
    InputStreamReader getInputStream(URL url)
}
