package net.bondar.models
/**
 * Accumulates information in the processing of the request and passes it an ResultObject.
 */
class InputObject {
    /**
     * Status of script response, should have the following format: "OK" or "ERROR".
     */
    private String status

    /**
     * Used when Google Places API return response without places.
     */
    private String comment

    /**
     * Used when errors occurred during the script.
     */
    private String errorMessage

    /**
     * List of places.
     */
    private List<Place> places = new ArrayList<>()

    InputObject() {
        this.comment = ""
        this.errorMessage = ""
    }

    InputObject(String errorMessage) {
        this.status = "ERROR"
        this.comment = ""
        this.errorMessage = errorMessage
    }

    InputObject(String status, String comment) {
        this.status = status
        this.comment = comment
        this.errorMessage = ""
    }

    String getStatus() {
        return status
    }

    void setStatus(String status) {
        this.status = status
    }

    String getComment() {
        return comment
    }

    void setComment(String comment) {
        this.comment = comment
    }

    String getErrorMessage() {
        return errorMessage
    }

    void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage
    }

    List<Place> getPlaces() {
        return places
    }

    void setPlaces(List<Place> places) {
        this.places = places
    }


    @Override
    public String toString() {
        return "InputObject{" +
                "status='" + status + '\'' +
                ", comment='" + comment + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", places=" + places +
                '}';
    }
}
