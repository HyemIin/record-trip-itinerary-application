package org.ybe.domain.itinerary.service;

import org.ybe.domain.itinerary.model.Itinerary;
import org.ybe.domain.itinerary.repository.ItineraryRepositoryProxy;
import org.ybe.domain.itinerary.view.ItineraryView;
import org.ybe.domain.trip.model.Trip;
import org.ybe.domain.trip.repository.TripRepositoryProxy;
import org.ybe.global.common.FileType;

import org.ybe.domain.itinerary.repository.ItineraryRepository;
import org.ybe.domain.itinerary.repository.ItineraryRepositoryProxy;
import org.ybe.domain.itinerary.view.ItineraryView;
import org.ybe.global.util.IdUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ItineraryServiceImpl implements ItineraryService{
    private ItineraryRepositoryProxy itineraryRepositoryProxy;
    private TripRepositoryProxy tripRepositoryProxy;

    public ItineraryServiceImpl() {
    }

    public ItineraryServiceImpl(TripRepositoryProxy tripRepositoryProxy,ItineraryRepositoryProxy itineraryRepositoryProxy) {
        this.tripRepositoryProxy = tripRepositoryProxy;
        this.itineraryRepositoryProxy = itineraryRepositoryProxy;
    }

    /**
     * 여행 이름으로 특정 여행을 찾는 메서드입니다.
     * 저장 된 모든 여행이 저장된 파일을 읽어들인 뒤,
     * 사용자가 찾으려고 하는 여행 이름과 일치하는 여행만을
     * 리스트에 담아 반환합니다. */
    @Override
    public Optional<Trip> findTripByTripId(String tripId, FileType fileType){
        try {
            List<Trip> tripList = tripRepositoryProxy.findAllByFileType(fileType);
            for (int i = 0; i < tripList.size(); i++) {
                if (tripList.get(i).getTripId() == Long.parseLong(tripId)) {
                    return Optional.ofNullable(tripList.get(i));
                }
            }
            throw new NullPointerException();

        }catch (NumberFormatException e){
            System.out.print(e.getMessage()+"값이 입력되었습니다. ");
        }
        return null;
    }

    /**
     * 여정 ID로 특정 여정을 찾는 메서드입니다.
     * 여행 이름으로 찾은 특정 여행과, 찾기를 원하는 여정의 ID를 매개변수로 입력받아
     * 특정 여행 안에서, 찾기를 원하는 여정의 ID가 일치하는 여정만을 반환합니다.
     * */
    @Override
    public Optional<Itinerary> findItineraryById(Optional<Trip> selectedTrip, Long itineraryId) {
        if (selectedTrip.isPresent()){
            Trip trip = selectedTrip.get();

            List<Itinerary> itineraryList = trip.getItineraries();
            for (int i = 0; i < itineraryList.size(); i++) {
                if (itineraryList.get(i).getItinerary_id().equals(itineraryId)) {
                    return Optional.ofNullable(itineraryList.get(i));
                }
            }
        }
        // 일치하는 항목을 찾지 못한 경우 빈 Optional을 반환합니다.
        return Optional.empty();
    }


    @Override
    public String isValid(String userInput) {
        if (userInput==null||userInput.equals("")){
            return null;
        }
        return userInput;
    }


    /**
     * 여정 신규 등록(1.여행등록) 시 호출하는 메서드
     * @param itineraryView
     */
    @Override
    public void recordItinerary(ItineraryView itineraryView){

        List itineraries = itineraryView.inputItinerary();
        itineraryRepositoryProxy.saveAll(IdUtil.getTripId()-1,itineraries);
        itineraries.clear();
        System.out.println("저장이 완료되었습니다. 메인화면으로 돌아갑니다.");

    }

    /**
     * 여정 추가 등록(2.여정기록) 시 호출하는 메서드
     * @param itineraryView
     * @param tripId
     */
    @Override
    public void appendItinerary(ItineraryView itineraryView, Long tripId) {
        if(tripId!=null){
            List itineraries = itineraryView.inputItinerary();
            itineraryRepositoryProxy.saveAll(tripId,itineraries);
            itineraries.clear();
            System.out.println("저장이 완료되었습니다. 메인화면으로 돌아갑니다.");
        }
    }

}


