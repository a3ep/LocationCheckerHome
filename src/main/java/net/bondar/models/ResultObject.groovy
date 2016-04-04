package net.bondar.models

class ResultObject {

    /**
     *
     */
    final String status

    /**
     *
     */
    final String errorMessage

    /**
     *
     */
    final List<Place> places = new ArrayList<>()

    ResultObject(String status) {
        //this.status = status
    }

    ResultObject(String status, String errorMessage) {
        this.status = status
        this.errorMessage = errorMessage
    }

    List<Place> getPlaces() {
        places.clone()
    }

    @Override
    public String toString() {
        "ResultObject{" +
                "status=" + status +
                ", places=" + places +
                ", errorMessage='" + errorMessage + '\'' +
                '}'
    }
}
