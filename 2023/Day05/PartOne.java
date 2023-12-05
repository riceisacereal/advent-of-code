import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Seed 79, soil 81, fertilizer 81, water 81, light 74, temperature 78, humidity 78, location 82
// 415125312 too high

public class PartOne {
    private static final String puzzleInput = "2023/Day05/input.txt";

    public static void main(String[] args) throws Exception {
        List<String> lines = readFile(puzzleInput);
        long result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static long[] getSeeds(String line) {
        long[] seeds = Arrays.stream(line.split(": ")[1].trim().split(" "))
            .mapToLong(Long::parseLong)
            .toArray();
        return seeds;
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

    public static long getCorresponding(ArrayList<long[]> maps, long num) {
        for (long[] map : maps) {
            long source = map[1];
            long dest = map[0];
            long offset = map[2];
            if (num >= source && num < source + offset) {
                return (num - source) + dest;
            }
        }
        return num;
    }

    public static long parseInput(List<String> lines) throws Exception {
        long[] seeds = getSeeds(lines.get(0));
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

        ArrayList<Long> locs = new ArrayList<>();
        for (long seed : seeds) {
            long currentNum = seed;
            for (ArrayList<long[]> section : allMaps) {
                currentNum = getCorresponding(section, currentNum);
//                System.out.print(currentNum + " ");
            }
            locs.add(currentNum);
//            System.out.println("\n");
        }

        return Collections.min(locs);
    }
}
