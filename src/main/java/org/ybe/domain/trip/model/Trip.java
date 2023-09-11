package org.ybe.domain.trip.model;

import lombok.Getter;
import lombok.ToString;
import org.ybe.domain.itinerary.dto.ItineraryDto.ItineraryJson;
import org.ybe.domain.itinerary.model.Itinerary;
import org.ybe.domain.trip.dto.TripDto;
import org.ybe.domain.trip.dto.TripDto.TripCsv;
import org.ybe.global.common.Constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.ybe.global.common.Constants.NULL;

@Getter
@ToString
public class Trip {
    private Long tripId;
    private String tripName;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Itinerary> itineraries = new ArrayList<>();

    public Trip(Long tripId, String tripName, LocalDate startDate, LocalDate endDate, List<Itinerary> itineraries) {
        this.tripId = tripId;
        this.tripName = tripName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.itineraries = itineraries;
    }

    public void addItinerary(Itinerary itinerary) {
        itineraries.add(itinerary);
    }

    public void setTripId(Long id) {
        this.tripId = id;
    }


    @Override
    public String toString() {
        return "=================" +"\n"+
                "여행 ID"+"\t\t"+"여행 이름"+"\t\t"+"시작날짜"+"\t\t"+"종료날짜"+"\t\t"+"여정 수"+"\n"+
                tripId+"\t\t\t"+tripName + "\t\t"+startDate+"\t"+endDate+"\t"+itineraries.size()+"개"+"\n"
                ;
    }
}
