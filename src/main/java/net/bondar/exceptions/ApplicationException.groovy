package net.bondar.exceptions
/**
 * Custom error.
 */
class ApplicationException extends RuntimeException {
    ApplicationException(String message = "") {
        super(message)
    }
}
