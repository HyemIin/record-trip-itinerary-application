package org.ybe.global.mapper;

import org.ybe.domain.itinerary.model.Itinerary;
import org.ybe.domain.trip.dto.TripDto;
import org.ybe.domain.trip.dto.TripDto.TripCsv;
import org.ybe.domain.trip.model.Trip;
import org.ybe.global.common.Constants;
import org.ybe.global.exception.InvalidCsvFormatException;
import org.ybe.global.util.TimeUtil;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.ybe.global.common.Constants.NULL;

public class CsvMapper {
    /**
     * Trip 클래스를 TripCsv 클래스로 변환해주는 메소드
     *
     * @param trip 변환할 trip
     * @return 변환된 TripCsv 리스트
     */
    public static List<TripCsv> tripToTripCsv(Trip trip) {
        if (trip.getItineraries().isEmpty()) {
            return List.of(TripCsv.builder()
                    .tripId(Long.toString(trip.getTripId()))
                    .tripName(trip.getTripName())
                    .startDate(trip.getStartDate().toString())
                    .endDate(trip.getEndDate().toString())
                    .itineraryId(NULL)
                    .departurePlace(NULL)
                    .destination(NULL)
                    .arriveTime(NULL)
                    .departureTime(NULL)
                    .checkIn(NULL)
                    .checkOut(NULL)
                    .build());
        } else {
            List<TripCsv> tripCsvList = new ArrayList<>();
            for (Itinerary itinerary : trip.getItineraries()) {
                tripCsvList.add(
                        TripCsv.builder()
                                .tripId(Long.toString(trip.getTripId()))
                                .tripName(trip.getTripName())
                                .startDate(trip.getStartDate().toString())
                                .endDate(trip.getEndDate().toString())
                                .itineraryId(Long.toString(itinerary.getItinerary_id()))
                                .departurePlace(itinerary.getDeparture_place())
                                .destination(itinerary.getDestination())
                                .arriveTime(itinerary.getArrive_time().format(DateTimeFormatter.ISO_DATE_TIME))
                                .departureTime(itinerary.getDeparture_time().format(DateTimeFormatter.ISO_DATE_TIME))
                                .checkIn(itinerary.getCheck_in().format(DateTimeFormatter.ISO_DATE_TIME))
                                .checkOut(itinerary.getCheck_out().format(DateTimeFormatter.ISO_DATE_TIME))
                                .build()
                );
            }
            return tripCsvList;
        }
    }

    /**
     * TripCsv 클래스를 String 배열로 변환해주는 메소드
     *
     * @param tripCsv 변환할 TripCsv
     * @return 변환된 String 배열
     */
    public static String[] tripCsvToStringArray(TripCsv tripCsv) {
        return new String[]{tripCsv.getTripId(), tripCsv.getTripName(), tripCsv.getStartDate(), tripCsv.getEndDate(), tripCsv.getItineraryId(), tripCsv.getDeparturePlace(), tripCsv.getDestination(), tripCsv.getDepartureTime(), tripCsv.getArriveTime(), tripCsv.getCheckIn(), tripCsv.getCheckOut()};
    }

    /**
     * String 배열을 TripCsv로 변환하는 메소드
     *
     * @param stringArray 변환할 String 배열
     * @return 변환된 TripCsv
     */
    public static TripCsv stringArrayToTripCsv(String[] stringArray) {
        try {
            return TripCsv.builder()
                    .tripId(stringArray[0])
                    .tripName(stringArray[1])
                    .startDate(stringArray[2])
                    .endDate(stringArray[3])
                    .itineraryId(stringArray[4])
                    .departurePlace(stringArray[5])
                    .destination(stringArray[6])
                    .departureTime(stringArray[7])
                    .arriveTime(stringArray[8])
                    .checkIn(stringArray[9])
                    .checkOut(stringArray[10])
                    .build();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidCsvFormatException();
        }

    }

    /**
     * List<TripCsv>를 TripCsv로 변환하는 메소드
     *
     * @param tripCsvList 변환할 TripCsv 리스트
     * @return 변환된 TripCsv
     */
    public static Trip tripCsvListToTrip(List<TripCsv> tripCsvList) {
        if (tripCsvList.isEmpty()) {
            return null;
        } else if (tripCsvList.size() == 1 && tripCsvList.get(0).getItineraryId().equals(Constants.NULL)) {
            TripCsv tripCsv = tripCsvList.get(0);
            return new Trip(Long.decode(tripCsv.getTripId()), tripCsv.getTripName(), TimeUtil.stringToLocalDate(tripCsv.getStartDate()), TimeUtil.stringToLocalDate(tripCsv.getEndDate()), new ArrayList<>());
        } else {
            TripCsv tripCsv = tripCsvList.get(0);
            Trip trip = new Trip(Long.decode(tripCsv.getTripId()), tripCsv.getTripName(), TimeUtil.stringToLocalDate(tripCsv.getStartDate()), TimeUtil.stringToLocalDate(tripCsv.getEndDate()), new ArrayList<>());
            for (TripCsv csv : tripCsvList) {
                trip.getItineraries().add(
                        Itinerary.builder()
                                .itinerary_id(Long.decode(csv.getItineraryId()))
                                .departure_place(csv.getDeparturePlace())
                                .destination(csv.getDestination())
                                .departure_time(TimeUtil.stringToLocalDateTime(csv.getDepartureTime()))
                                .arrive_time(TimeUtil.stringToLocalDateTime(csv.getArriveTime()))
                                .check_in(TimeUtil.stringToLocalDateTime(csv.getCheckIn()))
                                .check_out(TimeUtil.stringToLocalDateTime(csv.getCheckOut()))
                                .build(
                                ));
            }

            return trip;
        }
    }

}
