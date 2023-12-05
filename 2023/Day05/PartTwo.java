import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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

    public static long getDest(long source, long dest, long num) throws Exception {
        return num - source + dest;
    }

    public static long splitSearch(ArrayList<ArrayList<long[]>> allMaps, int index, long start, long end)
        throws Exception {
        if (index >= allMaps.size()) {
            return start;
        } else if (end <= allMaps.get(index).get(0)[1]) {
            return splitSearch(allMaps, index + 1, start, end);
        }

        ArrayList<long[]> maps = allMaps.get(index);
        for (long[] map : maps) {
            long sourceStart = map[1];
            long dest = map[0];
            long offset = map[2];
            long sourceEnd = sourceStart + offset;
            // end is not inclusive

            // start [ start ] end
            // source ( sourceStart ) sourceEnd
            // O - forward original range
            // X - calculate destination range
            // R - redo using same index and range
            if (start >= sourceStart && end <= sourceEnd) {
                // Contained ( [X] )
                return splitSearch(allMaps, index + 1, getDest(sourceStart, dest, start), getDest(sourceStart, dest, end));
            } else if (start < sourceStart && end > sourceEnd) {
                // Cover [O(X)R]
                long o = splitSearch(allMaps, index + 1, start, sourceStart);
                long x = splitSearch(allMaps, index + 1, getDest(sourceStart, dest, sourceStart), getDest(sourceStart, dest, sourceEnd));
                long r = splitSearch(allMaps, index, sourceEnd, end);
                return Math.min(o, Math.min(x, r));
            } else if (start < sourceStart) {
                // Second half in [O(X] )
                long o = splitSearch(allMaps, index + 1, start, sourceStart);
                long x = splitSearch(allMaps, index + 1, getDest(sourceStart, dest, sourceStart), getDest(sourceStart, dest, end));
                return Math.min(o, x);
            } else if (start < sourceEnd) {
                // First half in ( [X)R]
                long x = splitSearch(allMaps, index + 1, getDest(sourceStart, dest, start), getDest(sourceStart, dest, sourceEnd));
                long r = splitSearch(allMaps, index, sourceEnd, end);
                return Math.min(x, r);
            }
        }

        // No overlap () []
        return splitSearch(allMaps, index + 1, start, end);
    }

    public static long parseInput(List<String> lines) throws Exception {
        long[] seeds = Shared.getSeeds(lines.get(0));

        ArrayList<ArrayList<long[]>> allMaps = Shared.getAllMapsSortedOnSource(lines);
        ArrayList<Long> locs = new ArrayList<>();
        for (int i = 0; i < seeds.length; i += 2) {
            long start = seeds[i];
            long end = start + seeds[i + 1];

            // Every node returns smallest
            long result = splitSearch(allMaps, 0, start, end);
            locs.add(result);
        }

        return Collections.min(locs);

        // The following brute force code runs for 6 minutes but does give an answer,
        // I was a fool to not sit through it and instead spent 5 hours rewriting a solution

//        long minLoc = Long.MAX_VALUE;
//        for (int i = 0; i < seeds.length; i += 2) {
//            long start = seeds[i];
//            long end = start + seeds[i + 1];
//
//            for (long j = start; j < end; j++) {
//                long currentNum = j;
//                for (ArrayList<long[]> section : allMaps) {
//                    currentNum = getCorresponding(section, currentNum);
//                }
//                minLoc = Math.min(minLoc, currentNum);
//            }
//
//            System.out.println("Processed one more seed");
//        }
//
//        return minLoc;
    }
}
