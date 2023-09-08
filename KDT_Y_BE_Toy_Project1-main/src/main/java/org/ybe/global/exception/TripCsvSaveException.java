package org.ybe.global.exception;

public class TripCsvSaveException extends RuntimeException {
    public TripCsvSaveException() {
    }

    public TripCsvSaveException(String message) {
        super(message);
    }

    public TripCsvSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
