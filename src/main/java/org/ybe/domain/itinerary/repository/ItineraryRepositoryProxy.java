package org.ybe.domain.itinerary.repository;

import lombok.NoArgsConstructor;
import org.ybe.domain.itinerary.model.Itinerary;
import org.ybe.domain.trip.repository.TripCsvRepositoryImpl;
import org.ybe.domain.trip.repository.TripJsonRepositoryImpl;
import org.ybe.global.common.FileType;
import org.ybe.global.util.IdUtil;

import java.util.List;
import java.util.Optional;

/*
ItineraryRepository의 구현체입니다.
 */
@NoArgsConstructor
public class ItineraryRepositoryProxy implements ItineraryRepository {

    private final ItineraryJsonRepositoryImpl jsonImpl = new ItineraryJsonRepositoryImpl();
    private final ItineraryCsvRepositoryImpl csvImpl = new ItineraryCsvRepositoryImpl();

    /**
     * 여행에 속한 여정 정보를 리턴하는 메소드
     *
     * @param tripId   여행 아이디
     * @param fileType 조회할 파일 타입
     * @return 조회한 여정 리스트
     */
    @Override
    public List<Itinerary> findAllByTripIdAndFileType(Long tripId, FileType fileType) {
        List<Itinerary> toReturn;
        if (fileType.equals(FileType.CSV)) {
            toReturn = csvImpl.findAllByTripId(tripId);
        } else {
            ;
            toReturn = jsonImpl.findAllByTripId(tripId);
        }
        return toReturn;
    }

    /**
     * 여행에 속한 특정 여정을 아이디로 조회하는 메소드
     *
     * @param tripId      여행 아이디
     * @param itineraryId 여정 아이디
     * @param fileType    파일 타입
     * @return 조회하는 여정 정보
     */
    @Override
    public Optional<Itinerary> findByTripIdAndItineraryIdAndFileType(Long tripId, Long itineraryId, FileType fileType) {
        Optional<Itinerary> toReturn;
        if (fileType.equals(FileType.CSV)) {
            toReturn = csvImpl.findByTripIdAndItineraryId(tripId, itineraryId);
        } else {
            toReturn = jsonImpl.findByTripIdAndItineraryId(tripId, itineraryId);
        }
        return toReturn;
    }

    /**
     * 여정 정보를 JSON, CSV 파일에 저장하는 메소드
     *
     * @param tripId    여정이 속한 여행 아이디
     * @param itinerary 저장할 여정
     * @return 저장한 여정 정보
     */
    @Override
    public Itinerary save(Long tripId, Itinerary itinerary) {
        Long id = IdUtil.getItineraryId();

        jsonImpl.save(tripId, itinerary);
        csvImpl.save(tripId, itinerary);

        IdUtil.updateItineraryId(id + 1);
        return itinerary;
    }

    /**
     * 여정 정보들을 JSON, CSV 파일에 저장하는 메소드
     *
     * @param tripId      여정들이 속한 여행 아이디
     * @param itineraries 저장할 여정들
     * @return 저장한 여정 정보 리스트
     */
    @Override
    public List<Itinerary> saveAll(Long tripId, List<Itinerary> itineraries) {
        Long id = IdUtil.getItineraryId();

        jsonImpl.saveAll(tripId, itineraries);
        csvImpl.saveAll(tripId, itineraries);

        IdUtil.updateItineraryId(id + itineraries.size());
        return itineraries;
    }

}
