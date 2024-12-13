package attendance.service;

import attendance.domain.Crew;
import attendance.domain.Day;
import attendance.repository.CrewRepository;
import camp.nextstep.edu.missionutils.DateTimes;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceService {
    private static final String COMMA_SEPARATOR = ",";
    private static final String BLANK_SEPARATOR = " ";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm";
    private static final String FILE_PATH = "src/main/resources/attendances.csv";
    private static final String ATTENDANCE = "출석";
    private static final String LATENESS = "지각";
    private static final String ABSENCE = "결석";

    private static final String IS_DAY_OFF = "[ERROR] %d월 %d일 %s요일은 등교일이 아닙니다.";
    private static final String NOT_FOUND_CREW = "[ERROR] 등록되지 않은 닉네임입니다.";
    private static final String CAN_NOT_LOAD_FILE = "파일을 불러오는 데 문제가 발생했습니다.";
    private static final String ALREADY_ATTENDANCE = "[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.";
    private static final String AFTER_DAY = "[ERROR] 아직 수정할 수 없습니다.";

    public void init() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            reader.lines()
                    .skip(1)
                    .forEach(this::initCrew);
        } catch (IOException e) {
            throw new IllegalStateException(CAN_NOT_LOAD_FILE);
        }
    }

    private void initCrew(String line) {
        String[] tokens = line.split(COMMA_SEPARATOR);

        String name = tokens[0];
        String[] dateTime = tokens[1].split(BLANK_SEPARATOR);
        if (CrewRepository.findByName(name) == null) {
            CrewRepository.addCrew(new Crew(name));
        }
        initAttendance(dateTime, name);
    }

    private void initAttendance(String[] dateTime, String name) {
        String date = dateTime[0];
        String time = dateTime[1];

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate localDate = LocalDate.parse(date, dateFormatter);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        LocalTime localTime = LocalTime.parse(time, timeFormatter);

        Crew crew = CrewRepository.findByName(name);
        crew.addAttendance(localDate, localTime);
    }

    public void checkDayOff(LocalDate localDate) {
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        if (Day.isDayOff(day)) {
            throw new IllegalArgumentException(String.format(IS_DAY_OFF, month, day, Day.valueOfDay(day).getWeek()));
        }
    }

    public Crew findCrew(String name) {
        Crew crew = CrewRepository.findByName(name);
        if (crew == null) {
            throw new IllegalArgumentException(NOT_FOUND_CREW);
        }
        return crew;
    }

    public String checkAttendance(LocalDate localDate, LocalTime attendanceTime) {
        Day day = Day.valueOfDay(localDate.getDayOfMonth());
        LocalTime startTime = day.getStartTime();
        LocalTime attendance = startTime.plusMinutes(5);
        LocalTime lateness = startTime.plusMinutes(30);
        if (attendanceTime.isBefore(attendance) || attendanceTime.equals(attendance)) {
            return ATTENDANCE;
        }
        if (attendanceTime.isBefore(lateness) || attendanceTime.equals(lateness)) {
            return LATENESS;
        }
        return ABSENCE;
    }

    public void checkAlreadyAttendance(Crew crew, LocalDate localDate) {
        if (crew.attendances().containsKey(localDate)) {
            throw new IllegalArgumentException(ALREADY_ATTENDANCE);
        }
    }

    public void addAttendance(Crew crew, LocalDate localDate, LocalTime attendanceTime) {
        crew.addAttendance(localDate, attendanceTime);
    }

    public void checkAfterDay(int day) {
        LocalDateTime localDateTime = DateTimes.now();
        int nowDay = localDateTime.getDayOfMonth();
        if (day > nowDay) {
            throw new IllegalArgumentException(AFTER_DAY);
        }
    }

    public void calculateAttendanceState(Crew crew, LocalDate localDate, LocalTime attendanceTime) {
        if (attendanceTime == null || checkAttendance(localDate, attendanceTime).equals(ABSENCE)) {
            crew.plusAbsence();
            return;
        }
        if (checkAttendance(localDate, attendanceTime).equals(ATTENDANCE)) {
            crew.plusAttendance();
            return;
        }
        crew.plusLateness();
    }
}
