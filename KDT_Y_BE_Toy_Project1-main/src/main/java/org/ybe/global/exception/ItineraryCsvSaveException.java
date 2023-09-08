package org.ybe.global.exception;

public class ItineraryCsvSaveException extends RuntimeException {
    public ItineraryCsvSaveException() {
    }

    public ItineraryCsvSaveException(String message) {
        super(message);
    }

    public ItineraryCsvSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
