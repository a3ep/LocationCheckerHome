package net.bondar.models

class Place {
    private final def title
    private final def latitude
    private final def longitude
    private def distance

    Place(def title, def latitude, def longitude) {
        this.title = title
        this.latitude = latitude
        this.longitude = longitude
    }

    GString getTitle() {
        return title
    }

    GString getLatitude() {
        return latitude
    }

    GString getLongitude() {
        return longitude
    }

    void setDistance(distance) {
        this.distance = distance
    }


    @Override
    public String toString() {
        return "Place{" +
                "title=" + title +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", distance=" + distance +
                '}';
    }
}
