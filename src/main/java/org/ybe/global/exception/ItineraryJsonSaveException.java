package org.ybe.global.exception;

public class ItineraryJsonSaveException extends RuntimeException {
    public ItineraryJsonSaveException() {
    }

    public ItineraryJsonSaveException(String message) {
        super(message);
    }

    public ItineraryJsonSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
