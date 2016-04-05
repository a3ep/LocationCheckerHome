package net.bondar.models
/**
 * Contains the final result of the user request.
 */
class ResultObject {

    /**
     * Status of script response, should have the following format: "OK" or "ERROR".
     */
    final String status

    /**
     * Used when Google Places API return response without places.
     */
    final def comment

    /**
     * Used when errors occurred during the script.
     */
    final String errorMessage

    /**
     * List of places.
     */
    final List<Place> places = new ArrayList<>()

    ResultObject(String status) {
        this(status, "")
    }

    ResultObject(String status, def comment = "", String errorMessage) {
        this.status = status
        this.errorMessage = errorMessage
    }

    ResultObject(String status, String comment, String errorMessage) {
        this.status = status
        this.comment = comment
        this.errorMessage = errorMessage
    }

    List<Place> getPlaces() {
        places.clone()
    }

    void addPlaces(List<Place> places) {
        this.places.addAll(places)
    }

    @Override
    public String toString() {
        return "ResultObject{" +
                "status='" + status + '\'' +
                ", comment='" + comment + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", places=" + places +
                '}';
    }
}
