package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Crew {
    private final String name;
    private final Map<LocalDate, LocalTime> attendance = new HashMap<>();

    public Crew(String name) {
        this.name = name;
    }

    public void addAttendance(LocalDate localDate, LocalTime localTime) {
        attendance.put(localDate, localTime);
    }

    public String getName() {
        return name;
    }

    public Map<LocalDate, LocalTime> attendance() {
        return Map.copyOf(attendance);
    }
}
