package attendance.util;

import static attendance.constant.FunctionConstant.ATTENDANCE_CHANGE;
import static attendance.constant.FunctionConstant.ATTENDANCE_CHECK;
import static attendance.constant.FunctionConstant.CREW_ATTENDANCE_CHECK;
import static attendance.constant.FunctionConstant.EXPULSION_CHECK;
import static attendance.constant.FunctionConstant.QUIT;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class InputValidator {
    private static final Set<String> FUNCTION_INPUT =
            new HashSet<>(
                    Arrays.asList(ATTENDANCE_CHECK, ATTENDANCE_CHANGE, CREW_ATTENDANCE_CHECK, EXPULSION_CHECK, QUIT));
    private static final String TIME_REGEX = "^(0[0-9]|1[0-9]|2[0-3]):(0[1-9]|[0-5][0-9])$";
    private static final int START_TIME = 8;
    private static final int END_TIME = 23;
    private static final String ONLY_DIGIT_REGEX = "^[0-9]+$";
    private static final int MIN_DAY = 1;
    private static final int MAX_DAY = 31;

    private static final String INVALID_FORMAT = "[ERROR] 잘못된 형식을 입력하였습니다.";
    private static final String INVALID_TIME = "[ERROR] 캠퍼스 운영 시간에만 출석이 가능합니다.";

    private InputValidator() {
    }

    public static void validateFunction(String input) {
        if (!FUNCTION_INPUT.contains(input)) {
            throw new IllegalArgumentException(INVALID_FORMAT);
        }
    }

    public static void validateTime(String input) {
        if (!input.matches(TIME_REGEX)) {
            throw new IllegalArgumentException(INVALID_FORMAT);
        }
        String hour = input.split(":")[0];
        int attendanceTime = Integer.parseInt(hour);
        if (attendanceTime < START_TIME || attendanceTime > END_TIME) {
            throw new IllegalArgumentException(INVALID_TIME);
        }
    }

    public static void validateChangeDay(String input) {
        validateOnlyDigit(input);
        validateWithinIntRange(input);
        validateRange(Integer.parseInt(input));
    }

    private static void validateOnlyDigit(String input) {
        if (!input.matches(ONLY_DIGIT_REGEX)) {
            throw new IllegalArgumentException(INVALID_FORMAT);
        }
    }

    private static void validateWithinIntRange(String input) {
        BigInteger value = new BigInteger(input);
        if (value.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) < 0
                || value.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
            throw new IllegalArgumentException(INVALID_FORMAT);
        }
    }

    private static void validateRange(int number) {
        if (number < MIN_DAY || number > MAX_DAY) {
            throw new IllegalArgumentException(INVALID_FORMAT);
        }
    }
}
