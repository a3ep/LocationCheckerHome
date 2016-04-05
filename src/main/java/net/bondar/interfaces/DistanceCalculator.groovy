package net.bondar.interfaces
/**
 * Calculates distance between two geographical coordinates.
 */
interface DistanceCalculator {

    /**
     * Calculates distance between the specified coordinates.
     *
     * @param lat1 first point latitude
     * @param lng1 first point longitude
     * @param lat2 second point latitude
     * @param lng2 second point longitude
     * @return distance in metres between two points
     */
    double calculateDistance(String lat1, String lng1, String lat2, String lng2)
}