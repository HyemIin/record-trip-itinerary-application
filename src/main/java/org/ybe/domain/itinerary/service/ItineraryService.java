package org.ybe.domain.itinerary.service;

import org.ybe.domain.itinerary.model.Itinerary;
import org.ybe.domain.trip.model.Trip;
import org.ybe.domain.trip.repository.TripRepository;
import org.ybe.domain.trip.repository.TripRepositoryProxy;
import org.ybe.global.common.FileType;

import java.io.File;
import java.util.ArrayList;
import org.ybe.domain.itinerary.view.ItineraryView;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface ItineraryService {


    /**
     * 여정 신규 등록(1.여행등록) 시 호출하는 메서드
     * @param itineraryView
     * @throws FileNotFoundException
     */
    void recordItinerary(ItineraryView itineraryView) throws FileNotFoundException;

    List<Trip> selectedTrip = new ArrayList<>();

    public Optional<Trip> findTripByTripId(String tripId, FileType fileType);

    public Optional<Itinerary> findItineraryById(Optional<Trip> selectedTrip , Long itineraryId);

    public String isValid(String userInput);


    /**
     * 여정 추가 등록(2.여정기록) 시 호출하는 메서드
     * @param itineraryView
     * @param tripId
     */
    void appendItinerary(ItineraryView itineraryView, Long tripId);

}
