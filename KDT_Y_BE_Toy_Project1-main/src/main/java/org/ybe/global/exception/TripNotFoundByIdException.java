package org.ybe.global.exception;

import java.io.IOException;

public class TripNotFoundByIdException extends RuntimeException {
    public TripNotFoundByIdException() {
        super("해당 아이디로 존재하는 여행이 없습니다.");
    }

    public TripNotFoundByIdException(String message) {
        super(message);
    }

    public TripNotFoundByIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
