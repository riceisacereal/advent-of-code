import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// 393797665 too high

public class PartTwo {
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

    public static long getDest(long source, long dest, long num) throws Exception {
        return num - source + dest;
    }

    public static long splitSearch(ArrayList<ArrayList<long[]>> allMaps, int index, long start, long end)
        throws Exception {
        if (index >= allMaps.size()) {
            return start;
        }

        ArrayList<long[]> maps = allMaps.get(index);
        ArrayList<Long> locs = new ArrayList<>();
        for (long[] map : maps) {
            long sourceStart = map[1];
            long dest = map[0];
            long offset = map[2];
            long sourceEnd = sourceStart + offset;
            // end is not inclusive

            // start [ start ] end
            // source ( sourceStart ) sourceEnd
            if (end <= sourceStart) {
                // No overlap [O] ()
                return splitSearch(allMaps, index + 1, start, end);
            } else if (start >= sourceStart && end <= sourceEnd) {
                // Contained ( [X] )
                locs.add(splitSearch(allMaps, index + 1, getDest(sourceStart, dest, start), getDest(sourceStart, dest, end)));
                return Collections.min(locs);
            } else if (start < sourceStart && end > sourceEnd) {
                // Cover [O(X) ]
                locs.add(splitSearch(allMaps, index + 1, start, sourceStart));
                locs.add(splitSearch(allMaps, index + 1, getDest(sourceStart, dest, sourceStart), getDest(sourceStart, dest, sourceEnd)));
                start = sourceEnd;
            } else if (start < sourceStart) {
                // Second half in [O(X] )
                // Split
                locs.add(splitSearch(allMaps, index + 1, start, sourceStart));
                locs.add(splitSearch(allMaps, index + 1, getDest(sourceStart, dest, sourceStart), getDest(sourceStart, dest, end)));
                return Collections.min(locs);
            } else if (start < sourceEnd) {
                // First half in ( [X) ]
                // Split
                locs.add(splitSearch(allMaps, index + 1, getDest(sourceStart, dest, start), getDest(sourceStart, dest, sourceEnd)));
                start = sourceEnd;
            }

            if (start == end) {
                break;
            }
        }

        // No overlap () []
        if (start != end) {
            return splitSearch(allMaps, index + 1, start, end);
        }
        return Collections.min(locs);
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
        for (int i = 0; i < seeds.length; i += 2) {
            long start = seeds[i];
            long end = start + seeds[i + 1];

            // find range
            // every node returns smallest
            long result = splitSearch(allMaps, 0, start, end);
            locs.add(result);
//            System.out.print(result + " ");
        }

        System.out.println(locs);
        return Collections.min(locs);
    }
}
