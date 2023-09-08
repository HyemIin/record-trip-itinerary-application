package org.ybe.global.exception;

public class InvalidCsvFormatException extends RuntimeException {
    public InvalidCsvFormatException() {
        super("유효하지 않은 CSV 파일 형식으로 인해 조회에 실패하였습니다.");
    }

    public InvalidCsvFormatException(String message) {
        super(message);
    }

    public InvalidCsvFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
