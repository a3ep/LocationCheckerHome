package net.bondar.interfaces
/**
 * Interface for calculating distance between two geographical coordinates
 */
interface DistanceCalculator {
    double calculateDistance(String lat1, String lng1, String lat2, String lng2)
}