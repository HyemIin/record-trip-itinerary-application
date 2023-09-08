package org.ybe.domain.itinerary.view;

import org.ybe.domain.itinerary.model.Itinerary;
import org.ybe.domain.itinerary.service.ItineraryServiceImpl;
import org.ybe.domain.trip.model.Trip;
import org.ybe.domain.trip.repository.TripRepository;
import org.ybe.domain.trip.repository.TripRepositoryProxy;
import org.ybe.global.common.Answer;
import org.ybe.global.common.FileType;
import org.ybe.global.util.IdUtil;


import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.ybe.global.common.Constants.scanner;

public class ItineraryView {

    /**
     * 사용자가 입력하는 여정(itinerary) 정보를 리스트 타입으로 저장
     */
    private static List itineraryList = new ArrayList<>();
    private TripRepository tripRepository = new TripRepositoryProxy();

    public static void returnToMain() {
        System.out.println("메인 메뉴로 돌아갑니다.");
    }

    /**
     * 사용자에게 여정 정보를 반복적으로 입력받고 입력 내용을 출력하는 메서드
     * @return itineraryList
     */
    public List inputItinerary() {
        boolean flag = true;
        Answer answer = Answer.Y;
        do {
            try {
                itineraryList = createItinerary();
                flag = askContinueRecordItinerary(flag);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        while (flag);
        // 여정 List가 빈값이 아니라면 사용자가 이번 분기에 여정 정보 출력
        if (!itineraryList.isEmpty()) {
            try {
                for (Object iti : itineraryList) {
                    Itinerary itinerary = (Itinerary) iti;
                    System.out.println("여정 등록 순서 : " + (itineraryList.indexOf(iti)));
                    System.out.println("출발지 : " + itinerary.getDeparture_place());
                    System.out.println("도착지 : " + itinerary.getDestination());
                    System.out.println("출발 시간 : " + itinerary.getDeparture_time());
                    System.out.println("도착 시간 : " + itinerary.getArrive_time());
                    System.out.println("체크인 시간 : " + itinerary.getCheck_in());
                    System.out.println("체크아웃 시간 : " + itinerary.getCheck_out());
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("여정 정보가 올바르지 않습니다.");
            }
        }
        return itineraryList;
    }


    /**
     * 여정 정보를 계속 입력할 것인지 묻는 메서드
     * @param flag
     * @return flag(T/F)
     */
    private static boolean askContinueRecordItinerary(boolean flag) {
        Answer answer;
        System.out.print("여정을 계속 입력하시겠습니까? (Y/N)");
        answer = Answer.from(scanner.nextLine());

        if (answer != Answer.Y) {
            System.out.println("여정 등록을 중지합니다.");
            flag = false;
        }
        return flag;
    }


    /**
     * 여정 정보를 입력하는 메서드
     * @return itineraryList
     */
    public List createItinerary() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // DateTimeFormatter 객체 생성
            System.out.println("여정을 기록하세요.");
            System.out.print("출발지 : ");
            String departure_place = scanner.nextLine();
            if (departure_place.isEmpty()) {
                throw new IllegalArgumentException("출발지를 올바르게 입력하세요.");
            }

            System.out.print("도착지 : ");
            String destination = scanner.nextLine();
            if (destination.isEmpty()) {
                throw new IllegalArgumentException("도착지를 올바르게 입력하세요.");
            }

            System.out.print("출발 시간(yyyy-MM-dd HH:mm:ss) : ");
            String departureTime = scanner.nextLine();
            if (!isValidLocalDateTimeType(departureTime) || departureTime.isEmpty()) {
                throw new IllegalArgumentException("출발 시간이 올바르게 입력하세요.");
            }
            LocalDateTime depart_Time = LocalDateTime.parse(departureTime, formatter);

            System.out.print("도착 시간(yyyy-MM-dd HH:mm:ss) : ");
            String arrivaltime = scanner.nextLine();
            if (!isValidLocalDateTimeType(arrivaltime) || arrivaltime.isEmpty()) {
                throw new IllegalArgumentException("도착 시간이 올바르게 입력하세요.");
            }
            LocalDateTime arrive_Time = LocalDateTime.parse(arrivaltime, formatter);
            if (arrive_Time.isBefore(depart_Time)) {
                throw new IllegalArgumentException("도착 시간이 출발 시간보다 빠릅니다.");
            }

            System.out.print("체크인 시간(yyyy-MM-dd HH:mm:ss) : ");
            String check_in = scanner.nextLine();
            if (!isValidLocalDateTimeType(check_in) || check_in.isEmpty()) {
                throw new IllegalArgumentException("체크인 시간이 올바르게 입력하세요.");
            }
            LocalDateTime checkin_Time = LocalDateTime.parse(check_in, formatter);

            System.out.print("체크아웃 시간(yyyy-MM-dd HH:mm:ss) : ");
            String check_out = scanner.nextLine();
            if (!isValidLocalDateTimeType(check_out) || check_out.isEmpty()) {
                throw new IllegalArgumentException("체크아웃 시간이 올바르게 입력하세요.");
            }
            LocalDateTime checkout_Time = LocalDateTime.parse(check_out, formatter);
            if (checkout_Time.isBefore(checkin_Time)) {
                throw new IllegalArgumentException("체크아웃 시간이 체크인 시간보다 빠릅니다.");
            }
            Itinerary itinerary = new Itinerary(IdUtil.getItineraryId(), departure_place, destination, depart_Time, arrive_Time, checkin_Time, checkout_Time);
            itineraryList.add(itinerary);
        } catch (IllegalArgumentException e) {
            // 입력값이 유효하지 않으면 예외 메시지를 출력하고 다시 입력을 받습니다.
            System.out.println(e.getMessage());
            createItinerary(); // 재귀 호출
        }
        return itineraryList;
    }


    /**
     * 사용자가 입력한 여정 정보 중 날짜가 LocalDateTime 타입에 적합한지 검사하는 메서드
     * @param input
     * @return T/F
     */
    public boolean isValidLocalDateTimeType(String input) {
        // input이 yyyy-MM-dd HH:mm:ss 형식인지 정규식으로 검사
        String regex = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";
        return input.matches(regex);
    }
    /**
     * 사용자가 조회를 하기에 앞서 이미 저장된 여행과 여정을
     * 어떤 형식의 파일로 조회할지 선택하는 메서드 입니다.
     * 사용자 입력에 대하여 유효성 검사를 진행한뒤 early return 방식으로
     * 사용자 입력에 맞는 FileType을 반환합니다. */
    public FileType askForFileType(){
        System.out.print("조회를 원하는 파일의 번호를 입력하세요 (1.JSON 또는 2.CSV):");
        String userInputFileType = isValid(scanner.nextLine());
        if (userInputFileType.equals("1")||userInputFileType.equals("2")){
            FileType result = (userInputFileType.equals("1")) ? FileType.JSON : FileType.CSV;
            return result;
        }
        return null;

    }
    /**
     * 사용자가 조회를 원하는 FileType에 맞는 모든 여행 리스트를 출력하는 메서드입니다.
     * 등록된 여행이 없는 경우 RuntimeException을 발생시키고 컨트롤러에서 예외처리합니다.*/
    public void showTripListByUserInputFileType(FileType userInputFileType){
        isValid(userInputFileType.toString());
        if (userInputFileType==FileType.JSON){
            if (tripRepository.findAllByFileType(FileType.JSON).size() == 0){
                throw new RuntimeException();
            }
            System.out.println(tripRepository.findAllByFileType(FileType.JSON));

        }//else System.out.println(tripRepository.findAllByFileType(FileType.CSV));
        if (userInputFileType==FileType.CSV){
            if (tripRepository.findAllByFileType(FileType.JSON).size() == 0){
                throw new RuntimeException();
            }
            List<Trip> tripCsv = tripRepository.findAllByFileType(FileType.CSV);
            System.out.println(tripCsv);
        }
    }
    public Boolean showUserSelectedTripList(Optional<Trip> selectedTrip){
        if (selectedTrip.isPresent()) {
            Trip trip = selectedTrip.get();
            System.out.println("[선택하신 " + trip.getTripName()+" 여행의 정보입니다. ]");
            System.out.println("---------------------");
            System.out.println("여행 ID : " + trip.getTripId());
            System.out.println("여행 시작 날짜 : " + trip.getStartDate());
            System.out.println("여행 종료 날짜 : " + trip.getEndDate());
            System.out.println("--------------------");
            System.out.println("여정 목록 : ");
            if (trip.getItineraries().size() >0){
                for (int i = 0; i < trip.getItineraries().size(); i++) {
                    System.out.println("===================");
                    System.out.println("여정 ID"+"\t\t"+"출발지"+"\t"+"도착지");
                    System.out.println("\t"+trip.getItineraries().get(i).getItinerary_id()+"\t\t"+trip.getItineraries().get(i).getDeparture_place()+"\t\t"+trip.getItineraries().get(i).getDestination());
                    System.out.println("===================");
                }
                return true;
            }else {
                System.out.println("===================");
                System.out.println("여정이 존재하지 않습니다.");
                System.out.println("===================");
                return false;
            }

        }return null;
    }
    public void showUserSelectedItinerary(Optional<Itinerary> itinerary){
        if (itinerary.isPresent()){
            Itinerary iti = itinerary.get();
            System.out.println("===================");
            System.out.println("선택하신 "+iti.getItinerary_id()+"번 여정의 정보입니다.");
            System.out.println("===================");
            System.out.println("여정 ID : "+iti.getItinerary_id());
            System.out.println();
            System.out.println("출발 장소 : "+iti.getDeparture_place());
            System.out.println();
            System.out.println("도착 장소 : "+iti.getDestination());
            System.out.println();
            System.out.println("출발 시간 : "+iti.getDeparture_time());
            System.out.println();
            System.out.println("도착 시간 : "+iti.getArrive_time());
            System.out.println();
            System.out.println("체크인 시간 : "+iti.getArrive_time());
            System.out.println();
            System.out.println("체크아웃 시간 : "+iti.getCheck_out());
            System.out.println();
            System.out.println("=====즐거운 여행 되세요=====");
        }
        if (itinerary.isEmpty()){
            System.out.println("조회 된 여정이 없습니다.");
        }
    }

    /**
     * 사용자가 여정을 조회하려면 여행 조회가 선행되어야 합니다.
     * 어떤 여행을 선택할지 사용자 입력을 받는 메서드입니다.
     * */
    public String inputTripIdBeforeReadItinerary(){
        System.out.println("=====여행 리스트=====");
        //System.out.println(tripRepository.findAllByFileType(fileType));
        System.out.println("여정을 조회하려면 여정이 속한 여행을 먼저 조회해야 합니다.");
        System.out.print("조회를 원하는 여행의 여행 ID를 입력해주세요 : ");
        return isValid(scanner.nextLine());
    }

    /**
     * 조회를 원하는 여정을 식별할 ID를 입력받는 메서드입니다.
     * findItineraryById 메서드의 매개변수로 사용됩니다.
     * */
    public Long inputItineraryId(){
        System.out.print("조회를 원하는 여정의 ID를 입력해주세요 : ");
        try {
            return Long.parseLong(scanner.nextLine());
        }catch (NumberFormatException e){
            throw new RuntimeException("숫자를 입력해주세요");
        }
    }

    /**
     * 사용자가 키보드로 입력한 문자열에 대한 유효성 검사를 수행하는 메서드입니다.
     * Null 값이나 빈 문자열이 들어오면 예외를 터트립니다.
     * 유효한 양식의 값이 들어온다면 그대로 메서드를 빠져나옵니다. */
    public String isValid(String userInput){
        if (userInput==null || userInput.equals("")){
            return null;
        }
        return userInput;
    }

}
