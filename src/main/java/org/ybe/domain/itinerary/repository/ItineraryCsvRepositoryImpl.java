package org.ybe.domain.itinerary.repository;

import com.opencsv.CSVWriter;
import lombok.NoArgsConstructor;
import org.ybe.domain.itinerary.model.Itinerary;
import org.ybe.domain.trip.dto.TripDto.TripCsv;
import org.ybe.domain.trip.model.Trip;
import org.ybe.domain.trip.repository.TripCsvRepositoryImpl;
import org.ybe.domain.trip.repository.TripRepository;
import org.ybe.global.common.Constants;
import org.ybe.global.common.FileType;
import org.ybe.global.exception.ItineraryCsvSaveException;
import org.ybe.global.exception.TripNotFoundByIdException;
import org.ybe.global.mapper.CsvMapper;
import org.ybe.global.util.IdUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.ybe.global.common.FileType.CSV;

/**
 * 여정 CSV Repository입니다.
 */
@NoArgsConstructor
public class ItineraryCsvRepositoryImpl {
    private final String[] COLUMN = new String[]{"tripId", "tripName", "startDate", "endDate", "itineraryId", "departurePlace", "destination", "arriveTime", "departureTime", "checkIn", "checkOut"};
    private CSVWriter csvWriter;
    private final TripCsvRepositoryImpl tripRepository = new TripCsvRepositoryImpl();

    /**
     * 여행에 속한 여정 리스트를 반환하는 메소드
     *
     * @param tripId 여행 아이디
     * @return 여행 아이디에 속한 여정 리스트
     * @throws TripNotFoundByIdException 전달받은 여행 아이디로 여행을 찾을 수 없을때 발생하는 예외
     */
    public List<Itinerary> findAllByTripId(Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(TripNotFoundByIdException::new);
        return trip.getItineraries();
    }

    /**
     * 여행에 속한 특정 여정을 여정 아이디로 조회하는 메소드
     *
     * @param tripId      여행 아이디
     * @param itineraryId 여정 아이디
     * @return 조회한 여정을 옵셔널로 감싸서 리턴
     * @throws TripNotFoundByIdException 전달받은 여행 아이디로 여행을 찾을 수 없을때 발생하는 예외
     */
    public Optional<Itinerary> findByTripIdAndItineraryId(Long tripId, Long itineraryId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(TripNotFoundByIdException::new);
        for (Itinerary itinerary : trip.getItineraries()) {
            if (itinerary.getItinerary_id().equals(itineraryId)) {
                return Optional.of(itinerary);
            }
        }
        return Optional.empty();
    }

    /**
     * CSV 형식으로 여정 정보를 저장하는 메소드
     *
     * @param tripId    여정이 속한 여행 아이디
     * @param itinerary 저장할 여정
     * @return 저장된 여정 정보
     */
    public Itinerary save(Long tripId, Itinerary itinerary) {
        long id = IdUtil.getItineraryId();
        itinerary.setItinerary_id(id);
        Trip trip = tripRepository.findById(tripId).orElseThrow(TripNotFoundByIdException::new);
        trip.addItinerary(itinerary);
        saveTripToCsv(trip);
        return itinerary;
    }

    /**
     * CSV 형식으로 여정 정보들을 저장하는 메소드
     *
     * @param tripId      여정이 속한 여행 아이디
     * @param itineraries 저장할 여정들
     * @return 저장된 여정 리스트
     */
    public List<Itinerary> saveAll(Long tripId, List<Itinerary> itineraries) {
        long id = IdUtil.getItineraryId();
        Trip trip = tripRepository.findById(tripId).orElseThrow(RuntimeException::new);
        for (Itinerary itinerary : itineraries) {
            itinerary.setItinerary_id(id);
            trip.addItinerary(itinerary);
            id += 1;
        }
        saveTripToCsv(trip);
        return itineraries;
    }

    /**
     * 여행 아이디로 정보를 저장할 파일 이름을 결정하는 메소드
     *
     * @param id 여행 아이디
     * @return 저장할 파일 이름
     */
    private String determineFileName(Long id) {
        return Constants.TRIP_CSV_PREFIX + id + ".csv";
    }

    /**
     * CSV 파일에 저장하는 메소드
     *
     * @param trip 파일에 저장할 여행 객체
     * @throws ItineraryCsvSaveException csv 형식으로 파일을 저장하는 도중에 발생하는 예외
     */
    private void saveTripToCsv(Trip trip) {
        List<TripCsv> tripCsv = CsvMapper.tripToTripCsv(trip);
        try {
            csvWriter = new CSVWriter(new FileWriter(determineFileName(trip.getTripId())));
            csvWriter.writeNext(COLUMN);
            for (TripCsv csv : tripCsv) {
                csvWriter.writeNext(CsvMapper.tripCsvToStringArray(csv));
            }
            csvWriter.close();
        } catch (IOException e) {
            throw new ItineraryCsvSaveException("Csv 파일에 여정 정보를 저장하는 과정에서 예외가 발생하였습니다.");
        }

    }
}