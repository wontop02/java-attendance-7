package attendance.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private static final String REQUEST_FUNCTION = "오늘은 %d월 %d일 %s요일입니다. 기능을 선택해 주세요.\n"
            + "1. 출석 확인\n"
            + "2. 출석 수정\n"
            + "3. 크루별 출석 기록 확인\n"
            + "4. 제적 위험자 확인\n"
            + "Q. 종료";
    private static final String REQUEST_NAME = "닉네임을 입력해 주세요.";
    private static final String REQUEST_TIME = "등교 시간을 입력해 주세요.";
    private static final String REQUEST_ATTENDANCE_CHANGE_NAME = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private static final String REQUEST_CHANGE_DAY = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String REQUEST_CHANGE_TIME = "언제로 변경하겠습니까?";

    public String readFunction(int month, int day, String week) {
        System.out.println();
        System.out.printf(REQUEST_FUNCTION, month, day, week);
        System.out.println();
        return Console.readLine();
    }

    public String readName() {
        System.out.println(REQUEST_NAME);
        return Console.readLine();
    }

    public String readTime() {
        System.out.println(REQUEST_TIME);
        return Console.readLine();
    }

    public String readAttendanceChangeName() {
        System.out.println(REQUEST_ATTENDANCE_CHANGE_NAME);
        return Console.readLine();
    }

    public String readChangeDay() {
        System.out.println(REQUEST_CHANGE_DAY);
        return Console.readLine();
    }

    public String readChangeTime() {
        System.out.println(REQUEST_CHANGE_TIME);
        return Console.readLine();
    }
}
