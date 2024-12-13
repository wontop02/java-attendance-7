package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Crew {
    private final String name;
    private final Map<LocalDate, LocalTime> attendances = new HashMap<>();

    private int attendance = 0;
    private int lateness = 0;
    private int absence = 0;

    public Crew(String name) {
        this.name = name;
    }

    public void addAttendance(LocalDate localDate, LocalTime localTime) {
        attendances.put(localDate, localTime);
    }

    public String getName() {
        return name;
    }

    public Map<LocalDate, LocalTime> attendances() {
        return Map.copyOf(attendances);
    }

    public LocalTime getLocalTime(LocalDate localDate) {
        return attendances.get(localDate);
    }

    public void initState() {
        attendance = 0;
        lateness = 0;
        absence = 0;
    }

    public void plusAttendance() {
        attendance++;
    }

    public void plusLateness() {
        lateness++;
    }

    public void plusAbsence() {
        absence++;
    }

    public int getAttendance() {
        return attendance;
    }

    public int getLateness() {
        return lateness;
    }

    public int getAbsence() {
        return absence;
    }
}
