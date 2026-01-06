package attendance.domain;

import java.time.LocalTime;
import java.util.Arrays;

public enum Day {
    SUNDAY("일", 1, null),
    MONDAY("월", 2, LocalTime.of(13, 0)),
    THUEDAY("화", 3, LocalTime.of(10, 0)),
    WHENSDAY("수", 4, LocalTime.of(10, 0)),
    THURSDAY("목", 5, LocalTime.of(10, 0)),
    FRIDAY("금", 6, LocalTime.of(10, 0)),
    SATARDAY("토", 0, null);

    private final static int CHRISTMAS = 25;

    private final String week;
    private final int remain;
    private final LocalTime startTime;

    Day(String week, int remain, LocalTime startTime) {
        this.week = week;
        this.remain = remain;
        this.startTime = startTime;
    }

    public String getWeek() {
        return week;
    }

    public int getRemain() {
        return remain;
    }

    public LocalTime getStartTime() {
        return startTime;
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
}
