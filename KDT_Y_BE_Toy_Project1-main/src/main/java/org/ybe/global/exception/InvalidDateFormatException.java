package org.ybe.global.exception;

public class InvalidDateFormatException extends RuntimeException {
    public InvalidDateFormatException() {
        super("파일 날짜 형식에 오류가 있어서 파일을 불러오지 못하였습니다.");
    }

    public InvalidDateFormatException(String message) {
        super(message);
    }

    public InvalidDateFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
