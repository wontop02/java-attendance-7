package attendance.repository;

import attendance.domain.Crew;
import java.util.ArrayList;
import java.util.List;

public class CrewRepository {
    private static final List<Crew> crews = new ArrayList<>();

    public static List<Crew> crews() {
        return List.copyOf(crews);
    }

    public static void addCrew(Crew crew) {
        crews.add(crew);
    }

    public static Crew findByName(String name) {
        return crews.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
