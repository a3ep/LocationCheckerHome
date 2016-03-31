package net.bondar

class Place {
    private final def title
    private final def latitude
    private final def longitude

    Place(def title, def latitude, def longitude){
        this.title = title
        this.latitude = latitude
        this.longitude = longitude
    }

    def getTitle() {
        return title
    }

    def getLatitude() {
        return latitude
    }

    def getLongitude() {
        return longitude
    }


    @Override
    public String toString() {
        return "Place{" +
                "title=" + title +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
