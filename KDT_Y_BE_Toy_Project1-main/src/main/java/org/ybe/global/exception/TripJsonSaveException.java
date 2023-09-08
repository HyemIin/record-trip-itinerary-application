package org.ybe.global.exception;


public class TripJsonSaveException extends RuntimeException {
    public TripJsonSaveException() {
    }

    public TripJsonSaveException(String message) {
        super(message);
    }

    public TripJsonSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
