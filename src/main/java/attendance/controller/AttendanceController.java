package attendance.controller;

import static attendance.constant.FunctionConstant.ATTENDANCE_CHECK;

import attendance.service.AttendanceService;
import attendance.util.InputValidator;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;

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

    public String readFunction() {
        String function = inputView.readFunction();
        InputValidator.validateFunction(function);
        return function;
    }

    public void attendanceCheck() {
        LocalDateTime now = DateTimes.now();
        attendanceService.checkDayOff(now);
    }
}
