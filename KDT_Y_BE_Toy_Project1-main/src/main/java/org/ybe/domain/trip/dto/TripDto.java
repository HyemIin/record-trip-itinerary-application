package org.ybe.domain.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ybe.domain.itinerary.dto.ItineraryDto.ItineraryJson;
import org.ybe.domain.itinerary.model.Itinerary;
import org.ybe.domain.trip.model.Trip;
import org.ybe.global.common.Constants;
import org.ybe.global.util.TimeUtil;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TripDto {
    private long tripId;
    private String tripName;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Itinerary> itineraries = new ArrayList<>();

    /**
     * 여행 정보를 담는 TripDto 객체 생성
     *
     * @param tripName  여행 이름
     * @param startDate 여행 시작 날짜
     * @param endDate   여행 종료 날짜
     * @throws IllegalArgumentException 여행 시작 날짜가 여행 종료 날짜보다 미래인 경우 예외를 발생시킵니다.
     */
    public TripDto(String tripName, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(String.format("여행 시작일은 여행 종료일을 넘을 수 없습니다 startDate: %s, endDate: %s", startDate.toString(), endDate.toString()));
        }
        this.tripName = tripName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * TripDto 객체를 Trip 모델로 변환
     *
     * @return 변환된 Trip 모델 객체
     */
    public Trip toModel() {
        return new Trip(
                null,
                tripName,
                startDate,
                endDate,
                itineraries
        );
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TripJson {
        private Long tripId;
        private String tripName;
        private String startDate;
        private String endDate;
        private ItineraryJson[] itineraries;

    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TripCsv {
        private String tripId;
        private String tripName;
        private String startDate;
        private String endDate;
        private String itineraryId;
        private String departurePlace;
        private String destination;
        private String departureTime;
        private String arriveTime;
        private String checkIn;
        private String checkOut;




    }



}
