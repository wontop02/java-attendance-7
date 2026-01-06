package attendance.view;

public class OutputView {
    private static final String PRINT_ATTENDANCE_CHECK = "%s월 %s일 %s요일 %s (%s)";
    private static final String PRINT_ATTENDANCE_CHANGE = " -> %s (%s) 수정 완료!";

    public void printAttendanceCheck(int month, int day, String week, String time, String status) {
        System.out.println();
        System.out.printf(PRINT_ATTENDANCE_CHECK, month, day, week, time, status);
        System.out.println();
    }

    public void printAttendanceChange(int month, int day, String week, String time, String status, String changeTime,
                                      String changeStatus) {
        System.out.println();
        System.out.printf(PRINT_ATTENDANCE_CHECK, month, day, week, time, status);
        System.out.printf(PRINT_ATTENDANCE_CHANGE, changeTime, changeStatus);
        System.out.println();
    }
}
