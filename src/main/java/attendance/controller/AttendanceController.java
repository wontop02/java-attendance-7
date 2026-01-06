package attendance.controller;

import static attendance.constant.FunctionConstant.ATTENDANCE_CHECK;

import attendance.domain.Crew;
import attendance.domain.Day;
import attendance.service.AttendanceService;
import attendance.util.InputValidator;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceService attendanceService;

    public AttendanceController(InputView inputView, OutputView outputView, AttendanceService attendanceService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceService = attendanceService;
    }

    public void run() {
        attendanceService.init();
        String function = readFunction();
        if (function.equals(ATTENDANCE_CHECK)) {
            attendanceCheck();
        }
    }

    private String readFunction() {
        String function = inputView.readFunction();
        InputValidator.validateFunction(function);
        return function;
    }

    private void attendanceCheck() {
        LocalDateTime now = DateTimes.now();
        attendanceService.checkDayOff(now);
        Crew crew = findCrew();
        LocalTime localTime = readAttendanceTime();
        String status = attendanceService.checkAttendance(crew, now, localTime);
        attendanceService.addAttendance(crew, now, localTime);
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        outputView.printAttendanceCheck(month, day, Day.valueOfDay(day).getWeek(), localTime.toString(), status);
    }

    private Crew findCrew() {
        String name = inputView.readName();
        return attendanceService.findCrew(name);
    }

    private LocalTime readAttendanceTime() {
        String time = inputView.readTime();
        InputValidator.validateTime(time);
        return LocalTime.parse(time);
    }
}
