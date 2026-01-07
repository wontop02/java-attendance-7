package attendance.view;

import static attendance.constant.WarningConstant.NONE;

public class OutputView {
    private static final String PRINT_ATTENDANCE_CHECK = "%02d월 %02d일 %s요일 %s (%s)";
    private static final String PRINT_ATTENDANCE_CHANGE = " -> %s (%s) 수정 완료!";
    private static final String PRINT_ATTENDANCE_STATE = "\n\n출석: %d회\n"
            + "지각: %d회\n"
            + "결석: %d회";
    private static final String PRINT_WARNING = "%s 대상자입니다.";

    public void printAttendanceCheck(int month, int day, String week, String time, String status) {
        System.out.println();
        System.out.printf(PRINT_ATTENDANCE_CHECK, month, day, week, time, status);
    }

    public void printAttendanceChange(int month, int day, String week, String time, String status, String changeTime,
                                      String changeStatus) {
        System.out.println();
        System.out.printf(PRINT_ATTENDANCE_CHECK, month, day, week, time, status);
        System.out.printf(PRINT_ATTENDANCE_CHANGE, changeTime, changeStatus);
        System.out.println();
    }

    public void printAttendanceState(int attendance, int lateness, int absence) {
        System.out.printf(PRINT_ATTENDANCE_STATE, attendance, lateness, absence);
        System.out.println();
    }

    public void printWarning(String state) {
        if (state.equals(NONE)) {
            return;
        }
        System.out.printf(PRINT_WARNING, state);
        System.out.println();
    }
}
