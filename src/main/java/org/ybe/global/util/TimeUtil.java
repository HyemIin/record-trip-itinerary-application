package org.ybe.global.util;

import org.ybe.global.exception.InvalidDateFormatException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class TimeUtil {

    /**
     * 문자열을 LocalDateTime으로 변환해주는 메소드
     *
     * @param dateTime 변환할 문자열
     * @return 변환된 LocalDateTime
     * @throws InvalidDateFormatException 파일 날짜 형식 관련 오류
     */
    public static LocalDateTime stringToLocalDateTime(String dateTime) {
        String[] dateTimeArray = dateTime.split("T");
        try {

            return LocalDateTime.of(stringToLocalDate(dateTimeArray[0]), stringToLocalTime(dateTimeArray[1]));
        } catch (Exception e) {
            throw new InvalidDateFormatException();
        }
    }

    /**
     * 문자열을 LocalDate으로 변환해주는 메소드
     *
     * @param date 변환할 문자열
     * @return 변환된 LocalDate
     * @throws InvalidDateFormatException 파일 날짜 형식 관련 오류
     */
    public static LocalDate stringToLocalDate(String date) {
        int[] dateArray = Arrays.stream(date.split("-")).mapToInt(Integer::parseInt).toArray();

        try {

            return LocalDate.of(dateArray[0], dateArray[1], dateArray[2]);
        } catch (Exception e) {
            throw new InvalidDateFormatException();
        }

    }

    /**
     * 문자열을 LocalTime으로 변환해주는 메소드
     *
     * @param time 변환할 문자열
     * @return 변환된 LocalTime
     * @throws InvalidDateFormatException 파일 날짜 형식 관련 오류
     */
    private static LocalTime stringToLocalTime(String time) {
        int[] timeArray = Arrays.stream(time.split(":")).mapToInt(Integer::parseInt).toArray();

        try {

            return LocalTime.of(timeArray[0], timeArray[1], timeArray[2]);
        } catch (Exception e) {
            throw new InvalidDateFormatException();
        }
    }

}
