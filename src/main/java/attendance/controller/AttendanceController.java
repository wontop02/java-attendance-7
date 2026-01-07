package attendance.controller;

import static attendance.constant.FunctionConstant.ATTENDANCE_CHANGE;
import static attendance.constant.FunctionConstant.ATTENDANCE_CHECK;
import static attendance.constant.FunctionConstant.CREW_ATTENDANCE_CHECK;
import static attendance.constant.FunctionConstant.QUIT;

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
    private static final String ABSENT_FORMAT = "--:--";
    private static final String ABSENT = "결석";

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
        while (true) {
            String function = readFunction();
            if (function.equals(ATTENDANCE_CHECK)) {
                attendanceCheck();
            }
            if (function.equals(ATTENDANCE_CHANGE)) {
                attendanceChange();
            }
            if (function.equals(CREW_ATTENDANCE_CHECK)) {
                checkCrewAttendance();
            }
            if (function.equals(QUIT)) {
                return;
            }
        }
    }

    private String readFunction() {
        LocalDateTime now = DateTimes.now();
        String function = inputView.readFunction(now.getMonthValue(), now.getDayOfMonth(), Day.valueOfDay(
                now.getDayOfMonth()).getWeek());
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
        String beforeStatus = attendanceService.checkAttendance(localDate, crew.getLocalTime(localDate));
        String afterStatus = attendanceService.checkAttendance(localDate, localTime);
        outputView.printAttendanceChange(localDate.getMonthValue(), localDate.getDayOfMonth(),
                Day.valueOfDay(localDate.getDayOfMonth()).getWeek(), crew.getLocalTime(localDate).toString(),
                beforeStatus, localTime.toString(), afterStatus);
        attendanceService.addAttendance(crew, LocalDate.of(2024, 12, day), localTime);
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

    private void checkCrewAttendance() {
        Crew crew = readAttendanceCrew();
        crew.initState();
        printAttendance(crew);
        outputView.printAttendanceState(crew.getAttendance(), crew.getLateness(), crew.getAbsence());
        outputView.printWarning(attendanceService.checkWarningState(crew));
    }

    public void printAttendance(Crew crew) {
        for (int day = 1; day < DateTimes.now().getDayOfMonth(); day++) {
            LocalDate localDate = LocalDate.of(2024, 12, day);
            LocalTime localTime = crew.getLocalTime(localDate);
            if (Day.isDayOff(localDate.getDayOfMonth())) {
                continue;
            }
            attendanceService.calculateAttendanceState(crew, localDate, localTime);
            if (localTime == null) {
                outputView.printAttendanceCheck(localDate.getMonthValue(), localDate.getDayOfMonth(), Day.valueOfDay(
                        localDate.getDayOfMonth()).getWeek(), ABSENT_FORMAT, ABSENT);
                continue;
            }
            outputView.printAttendanceCheck(localDate.getMonthValue(), localDate.getDayOfMonth(), Day.valueOfDay(
                            localDate.getDayOfMonth()).getWeek(), localTime.toString(),
                    attendanceService.checkAttendance(localDate, localTime));
        }
    }
}
