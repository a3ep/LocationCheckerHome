package net.bondar.models

class Place {
    private final def TITLE
    private final def LATITUDE
    private final def LONGITUDE

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

    @Override
    public String toString() {
        return "Place{" +
                "TITLE=" + TITLE +
                ", LATITUDE=" + LATITUDE +
                ", LONGITUDE=" + LONGITUDE +
                '}';
    }
}
