package org.ybe.domain.trip.repository;

import lombok.NoArgsConstructor;
import org.ybe.domain.trip.model.Trip;
import org.ybe.global.common.Constants;
import org.ybe.global.common.FileType;
import org.ybe.global.util.IdUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/*
TripRepository의 구현체 입니다.
 */
@NoArgsConstructor
public class TripRepositoryProxy implements TripRepository {


    private final TripJsonRepositoryImpl jsonImpl = new TripJsonRepositoryImpl();
    private final TripCsvRepositoryImpl csvImpl = new TripCsvRepositoryImpl();

    /**
     * 저장된 모든 여행 정보를 파일 타입으로 조회하는 메소드
     *
     * @param fileType 조회할 파일 타입
     * @return 조회한 여행 정보 리스트
     */
    @Override
    public List<Trip> findAllByFileType(FileType fileType) {
        List<Trip> toReturn;
        if (fileType.equals(FileType.CSV)) {
            toReturn = csvImpl.findAll();
        } else {
            toReturn = jsonImpl.findAll();
        }

        return toReturn;
    }

    /**
     * 아이디로 특정 여행을 조회하는 메소드
     *
     * @param id       여행 아이디
     * @param fileType 조회할 파일 타입
     * @return 조회한 여행 정보
     */
    @Override
    public Optional<Trip> findByIdAndFileType(Long id, FileType fileType) {
        Optional<Trip> toReturn;
        if (fileType.equals(FileType.CSV)) {
            toReturn = csvImpl.findById(id);
        } else {
            toReturn = jsonImpl.findById(id);
        }

        return toReturn;
    }

    /**
     * 여행 정보를 JSON, CSV 형식으로 저장하는 메소드
     *
     * @param trip 저장할 여행
     * @return 저장된 여행 정보
     */
    @Override
    public Trip save(Trip trip) {
        long id = IdUtil.getTripId();


        jsonImpl.save(trip);
        csvImpl.save(trip);


        IdUtil.updateTripId(id + 1);
        return trip;
    }

    /**
     * 여행 정보들을 JSON, CSV 형식으로 저장하는 메소드
     *
     * @param trips 저장할 여행 리스트
     * @return 저장된 여행 정보 리스트
     */
    @Override
    public List<Trip> saveAll(List<Trip> trips) {
        long id = IdUtil.getTripId();

        jsonImpl.saveAll(trips);
        csvImpl.saveAll(trips);


        IdUtil.updateTripId(id + trips.size());
        return trips;
    }


}
