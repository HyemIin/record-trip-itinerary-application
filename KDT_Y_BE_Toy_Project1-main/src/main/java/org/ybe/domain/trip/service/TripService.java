package org.ybe.domain.trip.service;

import org.ybe.domain.trip.dto.TripDto;
import org.ybe.domain.trip.model.Trip;
import org.ybe.domain.trip.repository.TripRepository;
import org.ybe.global.common.Constants;
import org.ybe.global.common.FileType;

import java.util.List;
import java.util.Optional;

public class TripService {
    private TripRepository tripRepository;
    FileType fileType;

    public TripService( TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    /**
     * tripId를 포함한 모든 여행 관련 정보를 저장하는 메서드
     * @param tripDto 여행 객체
     */
    public void record(TripDto tripDto) { tripRepository.save(tripDto.toModel()); }

    /**
     * 파일 타입에 따라 TripRepository를 통해 가져온 모든 여행 목록을 TripController에 반환하는 메서드입니다.
     *
     * @param fileType 가져올 여행 목록의 파일 유형 (FileType.JSON 또는 FileType.CSV)
     * @return 파일 타입에 따라 가져온 모든 여행 목록
     */
    public List<Trip> getAllTrips(FileType fileType) {
        return tripRepository.findAllByFileType(fileType);
    }

    /**
     * 파일 타입에 따라 TripRepository를 통해 가져온 특정 여행 정보를 TripController에 반환하는 메서드입니다.
     *
     * @param tripId   가져올 여행의 ID
     * @param fileType 가져올 여행 정보의 파일 유형 (FileType.JSON 또는 FileType.CSV)
     * @return 파일 타입에 따라 가져온 특정 여행 정보
     */
    public Optional<Trip> getTripById(long tripId, FileType fileType) {
        return tripRepository.findByIdAndFileType(tripId, fileType);
    }

}
