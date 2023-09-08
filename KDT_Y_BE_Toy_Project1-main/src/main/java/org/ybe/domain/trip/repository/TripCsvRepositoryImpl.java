package org.ybe.domain.trip.repository;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import lombok.NoArgsConstructor;
import org.ybe.domain.trip.dto.TripDto.TripCsv;
import org.ybe.domain.trip.model.Trip;
import org.ybe.global.exception.TripCsvSaveException;
import org.ybe.global.mapper.CsvMapper;
import org.ybe.global.util.IdUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.ybe.global.common.Constants.TRIP_CSV_PREFIX;

@NoArgsConstructor
public class TripCsvRepositoryImpl {
    private final String[] COLUMN = new String[]{"tripId", "tripName", "startDate", "endDate", "itineraryId", "departurePlace", "destination", "arriveTime", "departureTime", "checkIn", "checkOut"};
    private CSVWriter csvWriter;
    private CSVReader csvReader;

    /**
     * CSV 형식으로 조회된 모든 여행 정보 조회 메소드
     *
     * @return 조회한 여행 정보 리스트
     * @throws RuntimeException 파일을 읽어오는 도중에 runtimeException 발생 가능
     */
    public List<Trip> findAll() {
        long id = 0L;
        List<Trip> trips = new ArrayList<>();
        while (true) {
            try {
                csvReader = new CSVReader(new FileReader(determineFileName(id)));
            } catch (FileNotFoundException e) {
                break;
            }
            try {
                csvReader.skip(1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            List<TripCsv> tripCsvList = new ArrayList<>();
            csvReader.forEach(strings -> tripCsvList.add(CsvMapper.stringArrayToTripCsv(strings)));
            Trip trip = CsvMapper.tripCsvListToTrip(tripCsvList);
            trips.add(trip);
            id += 1;
        }
        return trips;
    }

    /**
     * CSV 형식으로 저장된 여행 중 특정 아이디를 통해 여행을 조회하는 메소드
     *
     * @param id 여행 아이디
     * @return 조회한 여행 정보
     * @throws RuntimeException 파일을 읽어오는 도중에 runtimeException 발생 가능
     */
    public Optional<Trip> findById(Long id) {
        try {
            csvReader = new CSVReader(new FileReader(determineFileName(id)));
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
        try {
            csvReader.skip(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<TripCsv> tripCsvList = new ArrayList<>();
        csvReader.forEach(strings -> tripCsvList.add(CsvMapper.stringArrayToTripCsv(strings)));

        return Optional.ofNullable(CsvMapper.tripCsvListToTrip(tripCsvList));
    }

    /**
     * CSV 형식으로 여행을 저장하는 메소드
     *
     * @param trip 저장할 여행
     * @return 저장된 여행 정보
     * @throws TripCsvSaveException csv 형식으로 파일을 저장하는 도중에 발생하는 예외
     */
    public Trip save(Trip trip) throws TripCsvSaveException {
        if (trip.getTripId() == null) {
            long id = IdUtil.getTripId();
            trip.setTripId(id);
        }

        List<TripCsv> tripCsv = CsvMapper.tripToTripCsv(trip);
        try {
            csvWriter = new CSVWriter(new FileWriter(determineFileName(trip.getTripId())));
            csvWriter.writeNext(COLUMN);
            for (TripCsv csv : tripCsv) {
                csvWriter.writeNext(CsvMapper.tripCsvToStringArray(csv));
            }
            csvWriter.close();
        } catch (IOException e) {
            throw new TripCsvSaveException("Csv 파일에 여행을 저장할때 예외가 발생하였습니다.");
        }

        return trip;
    }

    /**
     * 여행 아이디 값으로 저장할 파일 이름을 결정하는 메소드
     *
     * @param id 여행 아이디
     * @return 파일 이름
     */
    private String determineFileName(long id) {
        return TRIP_CSV_PREFIX + id + ".csv";
    }

    /**
     * CSV 형식으로 여행들을 저장하는 메소드
     *
     * @param trips 저장할 여행
     * @return 저장된 여행 정보들
     * @throws TripCsvSaveException csv 형식으로 파일을 저장하는 도중에 발생하는 예외
     */
    public List<Trip> saveAll(List<Trip> trips) throws TripCsvSaveException {
        List<Trip> createdTrips = new ArrayList<>();
        long id = IdUtil.getTripId();
        for (Trip trip : trips) {
            trip.setTripId(id);
            List<TripCsv> tripCsv = CsvMapper.tripToTripCsv(trip);
            save(trip);
            createdTrips.add(trip);
            id += 1;
        }
        return createdTrips;
    }
}
