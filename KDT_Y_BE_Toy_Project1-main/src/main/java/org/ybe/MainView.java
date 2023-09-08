package org.ybe;

import static org.ybe.global.common.Constants.scanner;

public class MainView {
    /**
     * 메인 메뉴 5가지를 보여주는 메서드
     */
    public static void displayMainMenu() {
        System.out.println();
        System.out.println("\t\t\t\t\t\t[여행 여정 기록관리 서비스]");
        System.out.println("˚༉\uD83D\uDE96•┈┈┈┈＊┈┈┈┈＊┈┈┈┈＊┈┈┈┈＊┈┈┈┈＊┈┈┈┈＊┈┈┈┈＊┈┈┈┈＊┈┈┈┈＊┈┈┈┈＊┈┈┈┈•\uD83D\uDE96༉˚");
        System.out.println(
                """
                               ___  ___  ___   _____  _   _  ___  ___ _____  _   _  _   _\s
                               |  \\/  | / _ \\ |_   _|| \\ | | |  \\/  ||  ___|| \\ | || | | |
                               | .  . |/ /_\\ \\  | |  |  \\| | | .  . || |__  |  \\| || | | |
                               | |\\/| ||  _  |  | |  | . ` | | |\\/| ||  __| | . ` || | | |
                               | |  | || | | | _| |_ | |\\  | | |  | || |___ | |\\  || |_| |
                               \\_|  |_/\\_| |_/ \\___/ \\_| \\_/ \\_|  |_/\\____/ \\_| \\_/ \\___/\s
                        """);
        System.out.println("\t 1. 여행기록    2. 여정기록    3. 여행조회    4. 여정조회    5. 종료");
        System.out.println("˚༉\uD83D\uDE96•┈┈┈┈＊┈┈┈┈＊┈┈┈┈＊┈┈┈┈＊┈┈┈┈＊┈┈┈┈＊┈┈┈┈＊┈┈┈┈＊┈┈┈┈＊┈┈┈┈＊┈┈┈┈•\uD83D\uDE96༉˚");
    }

    /**
     * 사용자에게 메뉴 번호를 받아오는 메서드
     * @return 메뉴 번호
     */
    public static String getMenuNum() {
        System.out.print("원하시는 메뉴번호를 입력하세요: ");
        return scanner.nextLine();
    }
}
