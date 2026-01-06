package attendance.controller;

import attendance.domain.Crew;
import attendance.repository.CrewRepository;
import attendance.service.AttendanceService;

public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public void run() {
        attendanceService.init();
        for (Crew crew : CrewRepository.crews()) {
            System.out.println(crew.getName());
            System.out.println(crew.attendance());
        }
    }
}
