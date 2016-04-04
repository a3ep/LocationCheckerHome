package net.bondar.models

class ResultObject {

    /**
     *
     */
    final String status

    /**
     *
     */
    final String comment=""

    /**
     *
     */
    final String errorMessage

    /**
     *
     */
    final List<Place> places = new ArrayList<>()

    ResultObject(String status) {
        this(status, "")
    }

    ResultObject(String status, String errorMessage) {
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
