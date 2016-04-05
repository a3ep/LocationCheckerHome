package net.bondar.models
/**
 * Keeps places and pageToken for CalculationIterator.
 */
class PlaceKeeper {

    /**
     * Page token from Google Places API response.
     */
    private String placeToken = null

    /**
     * List of places.
     */
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
