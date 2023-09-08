package org.ybe.domain.trip.view;

import org.ybe.domain.trip.dto.TripDto;
import org.ybe.domain.trip.model.Trip;
import org.ybe.domain.itinerary.model.Itinerary;
import org.ybe.global.common.Answer;
import org.ybe.global.common.Constants;
import org.ybe.global.common.FileType;

import java.time.LocalDate;
import java.util.List;

import static org.ybe.global.common.Constants.*;

public class TripView {
    /**
     * 여행 기록 관련 input을 처리하는 메서드
     *
     * @return 입력받은 tripDto 객체
     */
    public static TripDto inputTrip() {
        while (true) {
            try {
                System.out.println("\n[여행 기록]");
                String tripName = inputTripName();
                LocalDate startDate = inputStartDate();
                LocalDate endDate = inputEndDate();
                return new TripDto(tripName, startDate, endDate);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 여행 이름 입력 메서드
     *
     * @return 여행 이름
     */
    private static String inputTripName() {
        System.out.print("여행 이름을 입력하세요: ");
        String tripName = scanner.nextLine();
        if (tripName.isBlank()) {
            throw new IllegalArgumentException(String.format("여행 이름은 1자 이상이어야 합니다. tripName: %s", tripName));
        }
        return tripName;
    }

    /**
     * 여행 시작 날짜 입력 메서드
     *
     * @return 여행 시작 날짜
     */
    private static LocalDate inputStartDate() {
        System.out.print("시작 날짜를 입력하세요(예 yyyy-MM-dd): ");
        return LocalDate.parse(scanner.nextLine());
    }

    /**
     * 여행 종료 날짜 입력 메서드
     *
     * @return 여행 종료 날짜
     */
    private static LocalDate inputEndDate() {
        System.out.print("종료 날짜를 입력하세요(예 yyyy-MM-dd): ");
        return LocalDate.parse(scanner.nextLine());
    }

    /**
     * 사용자로부터 조회할 파일 유형 (JSON 또는 CSV)을 입력받는 메서드입니다.
     *
     * @return 선택한 파일 유형 (FileType.JSON 또는 FileType.CSV)
     */
    public static FileType getInputFileType() {
        while (true) {
            System.out.print("조회를 원하는 파일의 번호를 입력하세요 (1.JSON 또는 2.CSV): ");
            int inputNumber;

            try {
                inputNumber = Integer.parseInt(Constants.scanner.nextLine());

                if (inputNumber == 1 || inputNumber == 2) {
                    return (inputNumber == 1) ? FileType.JSON : FileType.CSV;
                } else {
                    System.out.println("유효하지 않은 번호입니다. 1 또는 2를 입력하세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("유효하지 않은 입력입니다. 숫자를 입력하세요.");
            }
        }
    }

    /**
     * 사용자로부터 여행의 ID를 입력받는 메서드입니다.
     *
     * @return 사용자가 입력한 여행의 ID
     */
    public static long getInputTripId() {
        while (true) {
            System.out.print("여행 정보를 확인하고 싶은 여행의 id 입력해주세요: ");
            try {
                long tripId = Integer.parseInt(Constants.scanner.nextLine());
                return tripId;
            } catch (NumberFormatException e) {
                System.out.println("유효하지 않은 입력입니다. 숫자를 입력하세요.");
            }
        }
    }

    /**
     * TripController 통해 전달 받은 모든 여행 목록을 표시하는 메서드입니다.
     *
     * @param allTrips 모든 여행 목록
     */
    public static void displayAllTrips(List<Trip> allTrips) {
        if (allTrips.isEmpty()) {
            System.out.println("등록된 여행 정보가 없습니다.");
        } else {
            System.out.println("[여행 정보]");
            System.out.println("--------------------------");
            System.out.printf("%-10s %-20s%n", "여행 ID", "여행 이름");
            System.out.println("--------------------------");
            for (Trip trip : allTrips) {
                System.out.printf("%-10s %-20s%n", trip.getTripId(), trip.getTripName());
            }
            System.out.println();
        }
    }

    /**
     * TripController 통해 전달 받은 특정 여행 정보를 표시하는 메서드입니다.
     *
     * @param trip 특정 여행 정보
     */
    public static void displayTripById(Trip trip) {
        System.out.println("[여행 정보]");
        System.out.println("--------------------------");
        System.out.printf("%-16s %-20s%n", "여행 ID", trip.getTripId());
        System.out.printf("%-15s %-20s%n", "여행 이름", trip.getTripName());
        System.out.printf("%-15s %-20s%n", "시작 날짜", trip.getStartDate());
        System.out.printf("%-15s %-20s%n", "종료 날짜", trip.getEndDate());
        System.out.println("--------------------------");
        System.out.println();

        List<Itinerary> itineraries = trip.getItineraries();
        if (itineraries.isEmpty()) {
            System.out.println("해당 여행에 등록된 여정 정보가 없습니다.");
        } else {
            System.out.println("[여정 정보]");
            System.out.println("--------------------------");
            System.out.printf("%-15s %-20s%n", "출발지", "도착지");
            System.out.println("--------------------------");
            for (Itinerary itinerary : itineraries) {
                System.out.printf("%-15s %-20s%n", itinerary.getDeparture_place(), itinerary.getDestination());
            }
        }
    }

    /**
     * 메인 메뉴로 돌아갈건지 물어보는 메서드
     *
     * @return 사용자의 답변을 나타내는 Answer 객체
     */
    public static Answer askReturnToMain() {
        while (true) {
            try {
                System.out.print("메인 메뉴로 돌아가시겠습니까?(Y=메인 메뉴/N=여정 기록): ");
                return Answer.from(scanner.nextLine());
            } catch (Exception e) {
                System.out.println(e.getMessage() + "\n");
            }
        }
    }
}
