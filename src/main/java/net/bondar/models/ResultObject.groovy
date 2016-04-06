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
    final List<Place> places


    ResultObject(String errorMessage) {
        this.status = "ERROR"
        this.comment = ""
        this.errorMessage = errorMessage
        this.places = new ArrayList<>()
    }

    ResultObject(String status, String comment) {
        this.status = status
        this.comment = comment
        this.errorMessage = ""
        this.places = new ArrayList<>()
    }

    ResultObject(String status, List<Place> places) {
        this.status = status
        this.comment = ""
        this.errorMessage = ""
        this.places = places
    }

    List<Place> getPlaces() {
        places.clone()
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
