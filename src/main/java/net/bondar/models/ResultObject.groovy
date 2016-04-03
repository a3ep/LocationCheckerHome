package net.bondar.models

class ResultObject {
    private final def STATUS
    private List<Place> places
    private final String ERROR_MESSAGE = ""

    ResultObject(def status){
        this.STATUS = status
        this.places = new ArrayList<>()
    }

    ResultObject(def status, String errorMessage){
        this.STATUS = status
        this.ERROR_MESSAGE = errorMessage
    }

    List<Place> getPlaces() {
        return places
    }

    def getSTATUS() {
        return STATUS
    }

    String getERROR_MESSAGE() {
        return ERROR_MESSAGE
    }

    @Override
    public String toString() {
        return "ResultObject{" +
                "STATUS=" + STATUS +
                ", places=" + places +
                ", ERROR_MAEESGE='" + ERROR_MESSAGE + '\'' +
                '}';
    }
}
