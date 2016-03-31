package net.bondar.utils

class DistanceCalculator {
    private String specifiedLat
    private String specifiedLng

    DistanceCalculator(def specifiedLat, def specifiedLng){
        this.specifiedLat = Double.parseDouble(specifiedLat)
        this.specifiedLng = Double.parseDouble(specifiedLng)
    }

    def calculateDistance(String targetLat, String targetLng){
        double lat = Double.parseDouble(specifiedLat)
        double lng = Double.parseDouble(specifiedLng)
        double tLat = Double.parseDouble(targetLat)
        double tLng = Double.parseDouble(targetLng)
        double earthRadius = 6371000
        double dLat = Math.toRadians(tLat-lat)
        double dLng = Math.toRadians(tLng-lng)
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(tLat)) *
                    Math.sin(dLng/2) * Math.sin(dLng/2)
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
        def dist = (float) (earthRadius * c)
        return dist
    }
}
