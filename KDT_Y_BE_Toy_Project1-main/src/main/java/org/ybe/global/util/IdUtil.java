package org.ybe.global.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ybe.global.common.Constants;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class IdUtil {
    static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * 여행 아이디를 파일에서 읽어오는 메소드
     *
     * @return 여행 아이디
     */
    public static synchronized Long getTripId() {
        try {
            Reader reader = new FileReader(Constants.TRIP_ID_JSON);

            Map<String, Double> map = gson.fromJson(reader, Map.class);
            return map.get("id").longValue();
        } catch (FileNotFoundException e) {
            updateTripId(0L);
            return 0L;
        }

    }

    /**
     * 여행 아이디를 파일에 저장하는 메소드
     *
     * @param newId 저장할 여행 아이디
     */
    public static synchronized void updateTripId(Long newId) {
        try {
            Writer writer = new FileWriter(Constants.TRIP_ID_JSON);

            Map<String, Object> map = new HashMap<>();
            map.put("id", newId);
            gson.toJson(map, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 여정 아이디를 파일에서 읽어오는 메소드
     *
     * @return 여정 아이디
     */
    public static synchronized Long getItineraryId() {
        try {
            Reader reader = new FileReader(Constants.ITINERARY_ID_JSON);

            Map<String, Double> map = gson.fromJson(reader, Map.class);
            return map.get("id").longValue();
        } catch (FileNotFoundException e) {
            updateItineraryId(0L);
            return 0L;
        }
    }

    /**
     * 여정 아이디를 파일에 저장하는 메소드
     *
     * @param newId 저장할 여정 아이디
     */
    public static synchronized void updateItineraryId(Long newId) {
        try {
            Writer writer = new FileWriter(Constants.ITINERARY_ID_JSON);

            Map<String, Object> map = new HashMap<>();
            map.put("id", newId);
            gson.toJson(map, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
