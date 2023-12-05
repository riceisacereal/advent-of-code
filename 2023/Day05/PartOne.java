import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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
        long[] seeds = Shared.getSeeds(lines.get(0));

        ArrayList<ArrayList<long[]>> allMaps = Shared.getAllMapsSortedOnSource(lines);
        ArrayList<Long> locs = new ArrayList<>();
        for (long seed : seeds) {
            long currentNum = seed;
            for (ArrayList<long[]> section : allMaps) {
                currentNum = getCorresponding(section, currentNum);
            }
            locs.add(currentNum);
        }

        return Collections.min(locs);
    }
}
