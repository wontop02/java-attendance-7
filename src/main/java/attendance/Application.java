package attendance;

import attendance.controller.AttendanceController;
import attendance.service.AttendanceService;

public class Application {
    public static void main(String[] args) {
        AttendanceController attendanceController = new AttendanceController(new AttendanceService());
        attendanceController.run();
    }
}
