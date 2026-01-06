package attendance.controller;

import static attendance.constant.FunctionConstant.ATTENDANCE_CHANGE;
import static attendance.constant.FunctionConstant.ATTENDANCE_CHECK;

import attendance.domain.Crew;
import attendance.domain.Day;
import attendance.service.AttendanceService;
import attendance.util.InputValidator;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
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
        if (function.equals(ATTENDANCE_CHANGE)) {
            attendanceChange();
        }
    }

    private String readFunction() {
        String function = inputView.readFunction();
        InputValidator.validateFunction(function);
        return function;
    }

    private void attendanceCheck() {
        LocalDateTime now = DateTimes.now();
        attendanceService.checkDayOff(now.toLocalDate());
        Crew crew = readAttendanceCrew();
        LocalTime localTime = readAttendanceTime();
        String status = attendanceService.checkAttendance(now.toLocalDate(), localTime);
        attendanceService.checkAlreadyAttendance(crew, now.toLocalDate());
        attendanceService.addAttendance(crew, now.toLocalDate(), localTime);
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        outputView.printAttendanceCheck(month, day, Day.valueOfDay(day).getWeek(), localTime.toString(), status);
    }

    private Crew readAttendanceCrew() {
        String name = inputView.readName();
        return findCrew(name);
    }

    private Crew findCrew(String name) {
        return attendanceService.findCrew(name);
    }

    private LocalTime readAttendanceTime() {
        String time = inputView.readTime();
        InputValidator.validateTime(time);
        return LocalTime.parse(time);
    }

    private void attendanceChange() {
        Crew crew = readAttendanceChangeName();
        String input = inputView.readChangeDay();
        InputValidator.validateChangeDay(input);
        int day = Integer.parseInt(input);
        LocalDate localDate = LocalDate.of(2024, 12, day);
        attendanceService.checkDayOff(localDate);
        attendanceService.checkAfterDay(day);
        LocalTime localTime = readChangeTime();
        attendanceService.addAttendance(crew, LocalDate.of(2024, 12, day), localTime);
        String beforeStatus = attendanceService.checkAttendance(localDate, crew.getLocalTime(localDate));
        String afterStatus = attendanceService.checkAttendance(localDate, localTime);
        outputView.printAttendanceChange(localDate.getMonthValue(), localDate.getDayOfMonth(),
                Day.valueOfDay(localDate.getDayOfMonth()).getWeek(), crew.getLocalTime(localDate).toString(),
                beforeStatus, localTime.toString(), afterStatus);
    }

    private Crew readAttendanceChangeName() {
        String name = inputView.readAttendanceChangeName();
        return findCrew(name);
    }

    private LocalTime readChangeTime() {
        String time = inputView.readChangeTime();
        InputValidator.validateTime(time);
        return LocalTime.parse(time);
    }
}
