package org.ybe.domain.trip.repository;

import org.ybe.domain.trip.model.Trip;
import org.ybe.global.common.FileType;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * 서비스 계층에서 사용할 인터페이스, 구현체로는 TripRepositoryProxy가 존재합니다.
 */
public interface TripRepository {
    /**
     * 저장된 모든 여행 정보를 파일 타입으로 조회하는 메소드
     *
     * @param fileType 조회할 파일 타입
     * @return 조회한 여행 정보
     * @throws RuntimeException json, csv 파일 -> 여행으로 변환하는 과정이나 파일 읽어오는 과정에서 runtimeException 발생
     */
    List<Trip> findAllByFileType(FileType fileType) throws RuntimeException;

    /**
     * 여행 아이디로 특정 여행 정보를 조회하는 메소드
     *
     * @param id       여행 아이디
     * @param fileType 조회할 파일 타입
     * @return 조회한 여행 정보
     * @throws RuntimeException json, csv 파일 -> 여행으로 변환하는 과정이나 파일 읽어오는 과정에서 runtimeException 발생
     */
    Optional<Trip> findByIdAndFileType(Long id, FileType fileType) throws RuntimeException;

    /**
     * 여행을 JSON, CSV 형식으로 저장하는 메소드
     *
     * @param trip 저장할 여행
     * @return 저장한 여행 정보
     * @throws RuntimeException json, csv 파일 -> 여행으로 변환하는 과정이나 파일 저장하는 과정에서 runtimeException 발생
     */
    Trip save(Trip trip) throws RuntimeException;

    /**
     * 여행들을 각각 JSON, CSV 형식으로 저장하는 메소드
     *
     * @param trips 저장할 여행 리스트
     * @return 저장한 여행 리스트
     * @throws RuntimeException json, csv 파일 -> 여행으로 변환하는 과정이나 파일 저장하는 과정에서 runtimeException 발생
     */
    List<Trip> saveAll(List<Trip> trips) throws RuntimeException;
}
