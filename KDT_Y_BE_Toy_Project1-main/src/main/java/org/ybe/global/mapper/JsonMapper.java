package org.ybe.global.mapper;

import org.ybe.domain.itinerary.dto.ItineraryDto;
import org.ybe.domain.itinerary.dto.ItineraryDto.ItineraryJson;
import org.ybe.domain.itinerary.model.Itinerary;
import org.ybe.domain.trip.dto.TripDto;
import org.ybe.domain.trip.dto.TripDto.TripJson;
import org.ybe.domain.trip.model.Trip;
import org.ybe.global.exception.InvalidJsonFormatException;
import org.ybe.global.util.TimeUtil;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class JsonMapper {
    /**
     * Trip 클래스를 TripJson 클래스로 변환해주는 메소드
     *
     * @param trip 변환할 Trip
     * @return 변환된 TripJson
     */
    public static TripJson tripToTripJson(Trip trip) {
        return TripJson.builder()
                .tripId(trip.getTripId())
                .tripName(trip.getTripName())
                .startDate(trip.getStartDate().toString())
                .endDate(trip.getEndDate().toString())
                .itineraries(convertItineraryListToArray(trip))
                .build();
    }

    /**
     * 여정 리스트를 ItineraryJson 배열로 변환해주는 리스트
     *
     * @param trip 여정 리스트를 변환할 여행
     * @return ItineraryJson 배열
     */
    private static ItineraryJson[] convertItineraryListToArray(Trip trip) {
        return trip.getItineraries().stream().map(JsonMapper::itineraryToItineraryJson).toArray(ItineraryJson[]::new);
    }

    /**
     * TripJson을 Trip으로 변환해주는 메소드
     *
     * @param tripJson 변환할 TripJson
     * @return 변환된 Trip
     */
    public static Trip tripJsonToTrip(TripJson tripJson) {
        try {
            return new Trip(
                    tripJson.getTripId(),
                    tripJson.getTripName(),
                    TimeUtil.stringToLocalDate(tripJson.getStartDate()),
                    TimeUtil.stringToLocalDate(tripJson.getEndDate()),
                    new ArrayList<>(Arrays.stream(tripJson.getItineraries()).map(JsonMapper::itineraryJsonToItinerary).collect(Collectors.toList()))
            );
        } catch (NullPointerException e) {
            throw new InvalidJsonFormatException();
        }

    }

    /**
     * 여정을 ItineraryJson으로 변환해주는 메소드
     *
     * @param itinerary 변환할 itinerary
     * @return 변환된 ItineraryJson
     */
    public static ItineraryJson itineraryToItineraryJson(Itinerary itinerary) {

        return ItineraryJson.builder()
                .itineraryId(itinerary.getItinerary_id())
                .departurePlace(itinerary.getDeparture_place())
                .destination(itinerary.getDestination())
                .departureTime(itinerary.getDeparture_time().format(DateTimeFormatter.ISO_DATE_TIME))
                .arriveTime(itinerary.getArrive_time().format(DateTimeFormatter.ISO_DATE_TIME))
                .checkIn(itinerary.getCheck_in().format(DateTimeFormatter.ISO_DATE_TIME))
                .checkOut(itinerary.getCheck_out().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }

    /**
     * ItineraryJson을 Itinerary로 변환해주는 메소드
     *
     * @param itineraryJson 변환할 ItineraryJson
     * @return 변환된 Itinerary
     */
    public static Itinerary itineraryJsonToItinerary(ItineraryJson itineraryJson) {

        return Itinerary.builder()
                .itinerary_id(itineraryJson.getItineraryId())
                .departure_place(itineraryJson.getDeparturePlace())
                .destination(itineraryJson.getDestination())
                .departure_time(TimeUtil.stringToLocalDateTime(itineraryJson.getDepartureTime()))
                .arrive_time(TimeUtil.stringToLocalDateTime(itineraryJson.getArriveTime()))
                .check_in(TimeUtil.stringToLocalDateTime(itineraryJson.getArriveTime()))
                .check_out(TimeUtil.stringToLocalDateTime(itineraryJson.getCheckOut()))
                .build();
    }
}
