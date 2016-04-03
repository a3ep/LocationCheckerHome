package net.bondar.models

class Place {
    private final def TITLE
    private final def LATITUDE
    private final def LONGITUDE
    private def distance

    Place(def title, def latitude, def longitude){
        this.TITLE=title
        this.LATITUDE = latitude
        this.LONGITUDE = longitude
    }

    GString getTitle() {
        return TITLE
    }

    GString getLatitude() {
        return LATITUDE
    }

    GString getLongitude() {
        return LONGITUDE
    }

    void setDistance(distance) {
        this.distance = distance
    }


    @Override
    public String toString() {
        return "Place{" +
                "TITLE=" + TITLE +
                ", LATITUDE=" + LATITUDE +
                ", LONGITUDE=" + LONGITUDE +
                ", distance=" + distance +
                '}';
    }
}
