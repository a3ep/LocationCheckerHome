package net.bondar.impl

import net.bondar.interfaces.DistanceCalculator

/**
 * Calculates distance between two geographical coordinates
 */
class PlaceDistanceCalculator implements DistanceCalculator {

    /**
     * Calculates distance between the specified coordinates.
     *
     * @param lat1 first point latitude
     * @param lng1 first point longitude
     * @param lat2 second point latitude
     * @param lng2 second point longitude
     * @return distance in metres between two points
     */
    @Override
    double calculateDistance(String lat1, String lng1, String lat2, String lng2) {
        double lat = Double.parseDouble(lat1)
        double lng = Double.parseDouble(lng1)
        double tLat = Double.parseDouble(lat2)
        double tLng = Double.parseDouble(lng2)
        double earthRadius = 6371000
        double dLat = Math.toRadians(tLat - lat)
        double dLng = Math.toRadians(tLng - lng)
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(tLat)) *
                Math.sin(dLng / 2) * Math.sin(dLng / 2)
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        def dist = earthRadius * c
        return dist
    }
}