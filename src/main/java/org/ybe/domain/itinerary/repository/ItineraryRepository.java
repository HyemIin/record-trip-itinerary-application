package org.ybe.domain.itinerary.repository;

import org.ybe.domain.itinerary.model.Itinerary;
import org.ybe.global.common.FileType;

import java.util.List;
import java.util.Optional;

/**
 * 서비스 계층에서 사용할 인터페이스, 구현체로는 ItineraryRepositoryProxy가 존재합니다.
 */
public interface ItineraryRepository {
    /**
     * 파라미터로 전달받은 여행 아이디와 파일 타입으로 여정을 조회해서 여정 리스트를 반환하는 메소드
     *
     * @param tripId   여행 아이디
     * @param fileType 조회할 파일 타입
     * @return 조회한 여정 리스트
     * @throws RuntimeException json, csv 파일 -> 여정으로 변환하는 과정이나 파일 읽어오는 과정에서 runtimeException 발생
     */
    List<Itinerary> findAllByTripIdAndFileType(Long tripId, FileType fileType) throws RuntimeException;

    /**
     * 여행아이디, 여정아이디 그리고 파일 타입으로 여정 1개를 조회하는 메소드
     *
     * @param tripId      여행 아이디
     * @param itineraryId 여정 아이디
     * @param fileType    파일 타입
     * @return 조회한 여정의 옵셔널 객체
     * @throws RuntimeException json, csv 파일 -> 여정으로 변환하는 과정이나 파일 읽어오는 과정에서 runtimeException 발생
     */
    Optional<Itinerary> findByTripIdAndItineraryIdAndFileType(Long tripId, Long itineraryId, FileType fileType) throws RuntimeException;

    /**
     * 여정 1개를 저장하는 메소드
     *
     * @param tripId    여정이 속한 여행 아이디
     * @param itinerary 저장할 여정
     * @return 저장된 여정 정보
     * @throws RuntimeException json, csv 파일 -> 여정으로 변환하는 과정이나 파일 저장하는 과정에서 runtimeException 발생
     */
    Itinerary save(Long tripId, Itinerary itinerary) throws RuntimeException;

    /**
     * 여정 리스트를 저장하는 메소드
     *
     * @param tripId      여정들이 속한 여행 아이디
     * @param itineraries 저장할 여정들
     * @return 저장된 여정 리스트
     * @throws RuntimeException json, csv 파일 -> 여정으로 변환하는 과정이나 파일 읽어오는 과정에서 runtimeException 발생
     */
    List<Itinerary> saveAll(Long tripId, List<Itinerary> itineraries) throws RuntimeException;


}
