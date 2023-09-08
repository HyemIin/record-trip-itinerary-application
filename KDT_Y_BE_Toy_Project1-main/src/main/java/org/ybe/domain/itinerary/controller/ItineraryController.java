package org.ybe.domain.itinerary.controller;

import org.ybe.domain.itinerary.model.Itinerary;
import org.ybe.domain.itinerary.service.ItineraryService;
import org.ybe.domain.itinerary.service.ItineraryServiceImpl;
import org.ybe.domain.itinerary.view.ItineraryView;
import org.ybe.domain.trip.controller.TripController;
import org.ybe.domain.trip.model.Trip;
import org.ybe.global.common.FileType;

import java.util.Optional;

public class ItineraryController {
    private ItineraryView iv = new ItineraryView();
    private ItineraryServiceImpl itineraryService;

    public ItineraryController(ItineraryServiceImpl itineraryService) {
        this.itineraryService = itineraryService;

    }

    private ItineraryService itineraryServiceImpl = new ItineraryServiceImpl();


    /***
     * Input1 : 홈화면에서 여정 기록 클릭 -> 여행 조회에서 기록할 여행 리스트 입력 -> 특정 여행 number 입력 -> 특정 여행에 해당하는 여정 정보 입력창 제시
     * Input2 : 홈화면에서 여행 기록 클릭 -> 홈화면 복귀 No -> 방금 생성한 여행에 대한 여정 정보 입력창 제시
     * Output : 계속 기록 여부 No -> 방금 기록한 여정 List 출력
     */

    // 여정 신규 등록(1.여행등록) 시 호출하는 메서드
    public void record(ItineraryView itineraryView) {
        itineraryService.recordItinerary(itineraryView);
    }
    // 여정 추가 등록(2.여정기록) 시 호출하는 메서드
    public void append(ItineraryView itineraryView, TripController tripController) {
        itineraryService.appendItinerary(itineraryView, tripController.getTripId());
    }

    public void ItineraryReadController() {

        escapeLabel:
        while (true) {
            FileType userInputFileType;
            try {
                // 어떤 타입으로 조회할지 키보드 입력 받기
                userInputFileType = iv.askForFileType();
                iv.showTripListByUserInputFileType(userInputFileType);
                String userInputTripId;

                while (true) {
                    try {
                        // 여정을 검색할 특정 여행의 Id를 먼저 검색
                        userInputTripId = iv.inputTripIdBeforeReadItinerary();
                        Optional<Trip> trip = itineraryService.findTripByTripId(userInputTripId, userInputFileType);

                        if (iv.showUserSelectedTripList(trip)) { //선택한 여행에 여정이 있으면 true 반환, 여정이 없으면 false
                            while(true) {
                                try {
                                    Long itineraryId = iv.inputItineraryId(); //검색할 여정의 ID를 키보드 입력 받음
                                    Optional<Itinerary> userSelectedItinerary = itineraryService.findItineraryById(trip, itineraryId); //검색 결과 반환
                                    iv.showUserSelectedItinerary(userSelectedItinerary); //검색한 여정의 결과 출력, Optional로 NullPointerException 예방
                                    returnToMain();
                                    break escapeLabel;
                                }catch (RuntimeException e){
                                    System.out.println("유효한 여정 ID를 입력해주세요");
                                }
                            }
                        }
                        returnToMain();
                        break escapeLabel; // 선택한 여행에 여정이 없으면 메인 메뉴로 돌아감
                    } catch (NullPointerException e) {
                        System.out.println("유효한 여행 ID를 입력하세요");
                    }
                }
            } catch (NullPointerException e) {
                System.out.println("유효한 FileType을 입력하세요");
            } catch (RuntimeException e){
                System.out.println("등록된 여행이 없습니다.");
                returnToMain();
                break;
            }
        }
    }








    public void returnToMain() {

        ItineraryView.returnToMain();

    }



}
