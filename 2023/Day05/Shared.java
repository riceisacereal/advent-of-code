import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Shared {
    public static long[] getSeeds(String line) {
        return Arrays.stream(line.split(": ")[1].trim().split(" "))
            .mapToLong(Long::parseLong)
            .toArray();
    }

    public static ArrayList<long[]> sortMappings(ArrayList<String> mappings) {
        ArrayList<long[]> m = new ArrayList<>();
        for (int i = 1; i < mappings.size(); i++) {
            String line = mappings.get(i);
            long[] a = Arrays.stream(line.trim().split(" "))
                .mapToLong(Long::parseLong)
                .toArray();
            m.add(a);
        }

        m.sort(Comparator.comparingLong(a -> a[1]));
        return m;
    }

    public static ArrayList<ArrayList<long[]>> getAllMapsSortedOnSource(List<String> lines) {
        ArrayList<ArrayList<long[]>> allMaps = new ArrayList<>();
        ArrayList<String> mappings = new ArrayList<>();
        for (int i = 2 ;i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isEmpty()) {
                allMaps.add(sortMappings(mappings));
                mappings = new ArrayList<>();
                continue;
            } else if (i + 1 == lines.size()) {
                allMaps.add(sortMappings(mappings));
                mappings = new ArrayList<>();
                continue;
            }
            mappings.add(line);
        }

        return allMaps;
    }
}
