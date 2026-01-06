package attendance.util;

import static attendance.constant.FunctionConstant.ATTENDANCE_CHANGE;
import static attendance.constant.FunctionConstant.ATTENDANCE_CHECK;
import static attendance.constant.FunctionConstant.CREW_ATTENDANCE_CHECK;
import static attendance.constant.FunctionConstant.EXPULSION_CHECK;
import static attendance.constant.FunctionConstant.QUIT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class InputValidator {
    private static final String INVALID_FUNCTION_INPUT = "잘못된 형식을 입력하였습니다.";

    private static final Set<String> FUNCTION_INPUT =
            new HashSet<>(
                    Arrays.asList(ATTENDANCE_CHECK, ATTENDANCE_CHANGE, CREW_ATTENDANCE_CHECK, EXPULSION_CHECK, QUIT));

    private InputValidator() {
    }

    public static void validateFunction(String input) {
        if (!FUNCTION_INPUT.contains(input)) {
            throw new IllegalArgumentException(INVALID_FUNCTION_INPUT);
        }
    }
}
