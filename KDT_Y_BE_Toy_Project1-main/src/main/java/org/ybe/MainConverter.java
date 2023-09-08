package org.ybe;

import org.ybe.domain.itinerary.controller.ItineraryController;
import org.ybe.domain.itinerary.repository.ItineraryRepositoryProxy;
import org.ybe.domain.itinerary.service.ItineraryServiceImpl;
import org.ybe.domain.itinerary.view.ItineraryView;
import org.ybe.domain.trip.controller.TripController;
import org.ybe.domain.trip.dto.TripDto;
import org.ybe.domain.trip.repository.TripRepositoryProxy;
import org.ybe.domain.trip.service.TripService;
import org.ybe.global.common.Answer;

import java.io.FileNotFoundException;

import static org.ybe.global.common.Constants.*;

public class MainConverter {

    private final TripController tripController = new TripController(new TripService(new TripRepositoryProxy()));
    private final ItineraryController itineraryController = new ItineraryController(new ItineraryServiceImpl(new TripRepositoryProxy(), new ItineraryRepositoryProxy()));

    public void run() throws FileNotFoundException {
        while (true) {
            MainView.displayMainMenu();
            String userInput = MainView.getMenuNum();
            try {
                if (userInput.equals(TRIP_DISPLAY)) {
                    tripController.displayAllTrips();
                    tripController.displayTripById();
                } else if (userInput.equals(ITINERARY_DISPLAY)) {
                    itineraryController.ItineraryReadController();
                } else if (userInput.equals(TRIP_RECORD)) {
                    TripDto tripDto = tripController.input();
                    tripController.record(tripDto);
                    Answer answer = tripController.askReturnToMain();
                    if (answer.equals(Answer.N)) {
                        itineraryController.record(new ItineraryView());
                    }
                } else if (userInput.equals(ITINERARY_RECORD)) {
                    tripController.displayAllTrips();
                    tripController.displayTripById();
                    itineraryController.append(new ItineraryView(), tripController);
                } else if (userInput.equals(TERMINATE)) {
                    break;
                } else {
                    System.out.println("올바르지 않은 입력값입니다. 다시 입력해주세요");
                }
            } catch (RuntimeException e) {

                System.out.println("\n"+"❗️❗️❗️❗️❗️❗️❗️❗️❗️❗️❗️");
                System.out.println("예외가 발생하였습니다.");
                System.out.println("발생한 예외 정보 :" + e.getMessage());
                System.out.println("❗️❗️❗️❗️❗️❗️❗️❗️❗️❗️❗️"+"\n");

            }



        }
    }
}
