package org.ybe.domain.itinerary.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.ybe.domain.itinerary.dto.ItineraryDto.ItineraryJson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@Builder
@ToString
@AllArgsConstructor
public class Itinerary {
    private Long itinerary_id;
    private String departure_place;
    private String destination;
    private LocalDateTime departure_time;
    private LocalDateTime arrive_time;
    private LocalDateTime check_in;
    private LocalDateTime check_out;



    public void setItinerary_id(Long id) {
        this.itinerary_id = id;
    }


}
