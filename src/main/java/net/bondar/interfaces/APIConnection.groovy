package net.bondar.interfaces
/**
 * Interface for opening http-connection and receiving response data
 */
interface APIConnection {
    /**
     *
     * @param url
     * @return
     */
    InputStreamReader getInputStream(URL url)
}
