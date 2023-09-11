package org.ybe.domain.trip.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NoArgsConstructor;
import org.ybe.domain.trip.dto.TripDto.TripJson;
import org.ybe.domain.trip.model.Trip;
import org.ybe.global.common.Constants;
import org.ybe.global.exception.TripCsvSaveException;
import org.ybe.global.exception.TripJsonSaveException;
import org.ybe.global.mapper.JsonMapper;
import org.ybe.global.util.IdUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class TripJsonRepositoryImpl {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * JSON 형식으로 조회된 모든 여행 정보 조회 메소드
     *
     * @return 조회한 여행 정보 리스트
     * @throws RuntimeException 파일을 읽어오는 도중에 runtimeException 발생 가능
     */
    public List<Trip> findAll() {
        long id = 0L;
        List<Trip> trips = new ArrayList<>();
        Reader reader;
        while (true) {
            try {
                reader = new FileReader(determineFileName(id));
            } catch (FileNotFoundException e) {
                break;
            }
            trips.add(JsonMapper.tripJsonToTrip(gson.fromJson(reader, TripJson.class)));
            id += 1;

        }
        return trips;
    }

    /**
     * JSON 형식으로 저장된 여행 중 특정 아이디를 통해 여행을 조회하는 메소드
     *
     * @param id 여행 아이디
     * @return 조회한 여행 정보
     * @throws RuntimeException 파일을 읽어오는 도중에 runtimeException 발생 가능
     */
    public Optional<Trip> findById(Long id) {
        TripJson trip;
        Reader reader;
        try {
            reader = new FileReader(determineFileName(id));
            trip = gson.fromJson(reader, TripJson.class);
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
        return Optional.of(JsonMapper.tripJsonToTrip(trip));


    }

    /**
     * JSON 형식으로 여행을 저장하는 메소드
     *
     * @param trip 저장할 여행
     * @return 저장된 여행 정보
     * @throws TripJsonSaveException json 형식으로 파일을 저장하는 도중에 발생하는 예외
     */
    public Trip save(Trip trip) throws TripJsonSaveException {
        if (trip.getTripId() == null) {
            long id = IdUtil.getTripId();
            trip.setTripId(id);
        }
        Writer writer = null;
        try {
            writer = new FileWriter(determineFileName(trip.getTripId()));
            gson.toJson(JsonMapper.tripToTripJson(trip), writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new TripJsonSaveException("Json 파일에서 여행 데이터를 저장하는 과정에서 예외 발생하였습니다.");
        }


        return trip;
    }

    /**
     * 여행 아이디 값으로 저장할 파일 이름을 결정하는 메소드
     *
     * @param id 여행 아이디
     * @return 파일 이름
     */
    private String determineFileName(Long id) {
        return Constants.TRIP_JSON_PREFIX + id + ".json";
    }

    /**
     * JSON 형식으로 여행들을 저장하는 메소드
     *
     * @param trips 저장할 여행
     * @return 저장된 여행 정보들
     * @throws TripJsonSaveException json 형식으로 파일을 저장하는 도중에 발생하는 예외
     */
    public List<Trip> saveAll(List<Trip> trips) throws TripJsonSaveException {

        List<Trip> createdTrips = new ArrayList<>();
        long id = IdUtil.getTripId();
        for (Trip trip : trips) {
            trip.setTripId(id);
            save(trip);
            createdTrips.add(trip);
            id += 1;
        }


        return createdTrips;
    }


}
