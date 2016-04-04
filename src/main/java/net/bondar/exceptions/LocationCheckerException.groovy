package net.bondar.exceptions

class LocationCheckerException extends RuntimeException{

    LocationCheckerException() {

    }

    LocationCheckerException(String message){
        super(message)
    }
}
