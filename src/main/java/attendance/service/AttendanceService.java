package attendance.service;

import attendance.domain.Crew;
import attendance.repository.CrewRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceService {
    private static final String COMMA_SEPARATOR = ",";
    private static final String BLANK_SEPARATOR = " ";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm";
    private static final String FILE_PATH = "src/main/resources/attendances.csv";

    private static final String NOT_FOUND_CREW = "등록되지 않은 닉네임입니다.";
    private static final String CAN_NOT_LOAD_FILE = "파일을 불러오는 데 문제가 발생했습니다.";

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
}
