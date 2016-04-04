package net.bondar.interfaces
/**
 * Interface for opening http-connection and receiving response data
 */
interface DataChecker {
    def getResponseData(def url)
}
