package attendance.domain;

import java.util.Arrays;

public enum Day {
    SUNDAY("일", 1),
    MONDAY("월", 2),
    THUEDAY("화", 3),
    WHENSDAY("수", 4),
    THURSDAY("목", 5),
    FRIDAY("금", 6),
    SATARDAY("토", 0);

    private final static int CHRISTMAS = 25;

    private final String week;
    private final int remain;

    Day(String week, int remain) {
        this.week = week;
        this.remain = remain;
    }

    public String getWeek() {
        return week;
    }

    public int getRemain() {
        return remain;
    }

    public static Day valueOfDay(int day) {
        int dayRemain = day % 7;
        return Arrays.stream(values())
                .filter(d -> d.getRemain() == dayRemain)
                .findFirst()
                .orElse(null);
    }

    public static boolean isDayOff(int day) {
        int dayRemain = day % 7;
        return day == CHRISTMAS || dayRemain == SUNDAY.getRemain() || dayRemain == SATARDAY.getRemain();
    }

    public static boolean isMonday(int day) {
        int dayRemain = day % 7;
        return dayRemain == MONDAY.getRemain();
    }
}
