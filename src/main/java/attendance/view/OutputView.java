package attendance.view;

public class OutputView {
    private static final String PRINT_ATTENDANCE_CHECK = "%d월 %d일 %s요일 %s (%s)";

    public void printAttendanceCheck(int month, int day, String week, String time, String status) {
        System.out.println();
        System.out.printf(PRINT_ATTENDANCE_CHECK, month, day, week, time, status);
        System.out.println();
    }
}
