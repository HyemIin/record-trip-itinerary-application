package org.ybe.domain.itinerary.dto;


import lombok.*;
import org.ybe.domain.itinerary.model.Itinerary;
import org.ybe.global.util.TimeUtil;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class ItineraryDto {
    private Long itinerary_id;
    private String departure_place;
    private String destination;
    private LocalDateTime departure_time;
    private LocalDateTime arrive_time;
    private LocalDateTime check_in;
    private LocalDateTime check_out;


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItineraryJson {
        private Long itineraryId;
        private String departurePlace;
        private String destination;
        private String departureTime;
        private String arriveTime;
        private String checkIn;
        private String checkOut;

    }
}
