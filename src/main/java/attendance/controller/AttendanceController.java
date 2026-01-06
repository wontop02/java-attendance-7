package attendance.controller;

import attendance.service.AttendanceService;
import attendance.util.InputValidator;
import attendance.view.InputView;
import attendance.view.OutputView;

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
        String function = inputView.readFunction();
        InputValidator.validateFunction(function);
    }
}
