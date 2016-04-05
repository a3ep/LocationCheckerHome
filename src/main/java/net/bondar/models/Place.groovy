package net.bondar.models
/**
 * Place entity.
 */
class Place {

    /**
     * Place title.
     */
    private final def title

    /**
     * Place latitude.
     */
    private final def latitude

    /**
     * Place longitude.
     */
    private final def longitude

    /**
     * Distance between place coordinates and the specified coordinates.
     */
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
