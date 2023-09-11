package org.ybe.global.common;

import java.util.Scanner;

public class Constants {
    public static final Scanner scanner = new Scanner(System.in);

    public static final String TRIP_ID_JSON = "data/tripId.json";
    public static final String ITINERARY_ID_JSON = "data/itineraryId.json";
    public static final String TRIP_JSON_PREFIX = "data/json/trip_";
    public static final String TRIP_CSV_PREFIX = "data/csv/trip_";
    public static final String NULL = "NULL";

    public static String TRIP_RECORD = "1";
    public static String ITINERARY_RECORD = "2";
    public static String TRIP_DISPLAY = "3";
    public static String ITINERARY_DISPLAY = "4";
    public static String TERMINATE = "5";
}
