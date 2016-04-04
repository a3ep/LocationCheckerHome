package net.bondar.models
/**
 *
 */
class PlaceKeeper {
    private String placeToken = null
    private List<Place> places = new ArrayList<>()


    String getPlaceToken() {
        return placeToken
    }

    void setPlaceToken(String placeToken) {
        this.placeToken = placeToken
    }

    List<Place> getPlaces() {
        return places
    }

    void setPlaces(List<Place> places) {
        this.places = places
    }
}
