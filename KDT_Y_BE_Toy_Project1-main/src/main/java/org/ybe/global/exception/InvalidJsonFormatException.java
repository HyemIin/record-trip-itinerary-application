package org.ybe.global.exception;

public class InvalidJsonFormatException extends RuntimeException {
    public InvalidJsonFormatException() {
        super("유효하지 않은 JSON 파일 형식으로 인해 조회에 실패하였습니다.");
    }

    public InvalidJsonFormatException(String message) {
        super(message);
    }

    public InvalidJsonFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
