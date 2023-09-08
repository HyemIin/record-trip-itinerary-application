package org.ybe.domain.trip.controller;

import lombok.Getter;
import org.ybe.domain.trip.dto.TripDto;
import org.ybe.domain.trip.model.Trip;
import org.ybe.domain.trip.service.TripService;
import org.ybe.domain.trip.view.TripView;

import java.util.List;
import java.util.Optional;

import org.ybe.global.common.Answer;
import org.ybe.global.common.Constants;
import org.ybe.global.common.FileType;

public class TripController {
    private TripService tripService;
    private FileType fileType;
    private List<Trip> allTrips;

    @Getter
    private Long tripId;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    /**
     * 주어진 TripDto 객체를 사용하여 여행 정보를 기록
     *
     * @param tripDto 여행 정보를 포함한 TripDto 객체
     */
    public void record(TripDto tripDto) {
        tripService.record(tripDto);
    }

    /**
     * 사용자로부터 여행 정보를 입력받아 TripDto 객체를 반환
     *
     * @return 입력받은 여행 정보를 담고 있는 TripDto 객체
     */
    public TripDto input() {
        return TripView.inputTrip();
    }

    /**
     * 사용자에게 메인 메뉴로 돌아가는지 묻는 질문을 보여주고 사용자의 답변을 반환
     *
     * @return 사용자의 답변을 나타내는 Answer 객체
     */
    public Answer askReturnToMain() {
        return TripView.askReturnToMain();
    }

    /**
     * 모든 여행 목록을 조회하고 화면에 표시하는 메서드입니다.
     * 사용자로부터 파일 유형을 입력받고 해당 파일 유형의 모든 여행 정보를 가져와서 화면에 표시합니다.
     */
    public void displayAllTrips() {
        fileType = TripView.getInputFileType();
        allTrips = tripService.getAllTrips(fileType);
        TripView.displayAllTrips(allTrips);
    }

    /**
     * 특정 여행 정보를 조회하고 화면에 표시하는 메서드입니다.
     * 여행 목록이 비어있으면 아무 작업도 수행하지 않고 메서드를 종료합니다.
     * 사용자로부터 여행 아이디를 입력받고 해당하는 특정 여행 정보를 가져와서 화면에 표시합니다.
     */
    public void displayTripById() {
        if (allTrips.isEmpty()) {
            tripId = null;
            return;
        }
        while (true) {
            tripId = TripView.getInputTripId();
            Optional<Trip> trip = tripService.getTripById(tripId, fileType);
            if (trip.isPresent()) {
                TripView.displayTripById(trip.get());
                break;
            } else {
                System.out.println("해당 ID로 여행 정보를 찾을 수 없습니다.");
            }
        }
    }
}
