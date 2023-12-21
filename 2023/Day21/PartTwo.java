import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 10321330383883446986 too high
// 10321329602941223166 too high - i am using the 26501365 instead of dividing
// 602219006907038 too high - didn't think about alternating odd/even?
// 601441080564338 not right - didn't think about small triangles being even
// 601441063166538 YES

public class PartTwo {
    private static final String puzzleInput = "2023/Day21/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        long result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static long parseInput(List<String> lines) {
        final int maxY = lines.size();
        final int maxX = lines.get(0).length();
        final int STEPS = 26501365;
        final int N = (STEPS - 65) / 131;

        long total = 0;

        int range = 131 - 1;
        int[][] coords = new int[][] {
            {0, maxX / 2},
            {maxY / 2, 0},
            {maxY / 2, maxX - 1},
            {maxY - 1, maxX / 2}
        };
        // calculate corners
        for (int[] coord : coords) {
            total += Shared.countReachable(lines, range, new int[] {coord[0], coord[1]});
        }

        range = 64; // small triangles are even
        int small = 0;
        coords = new int[][] {
            {0, 0},
            {maxY - 1, 0},
            {0, maxX - 1},
            {maxY - 1, maxX - 1}
        };
        // calculate small triangles
        for (int[] coord : coords) {
            small += Shared.countReachable(lines, range, new int[] {coord[0], coord[1]});
        }
        total += (long) small * N;

        range = 64 + 131; // big triangles are odd
        int big = 0;
        // calculate big triangles
        for (int[] coord : coords) {
            big += Shared.countReachable(lines, range, new int[] {coord[0], coord[1]});
        }
        total += (long) big * (N - 1);

        // odd full
        range = 201; // big number just in case
        long oddFull = Shared.countReachable(lines, range, new int[] {maxY / 2, maxX / 2});

        // even full
        range = 200; // big number just in case
        long evenFull = Shared.countReachable(lines, range, new int[] {maxY / 2, maxX / 2});

        // count even and odd
        // starts with 1 odd in the middle
        // then goes 4e, 8o, 12e, 16o,...
        long odd = 1;
        long even = 0;
        for (int i = 1; i < N; i++) { // don't count outer odd border
            if (i % 2 == 1) { // on even blocks
                even += i * 4;
            } else {
                odd += i * 4;
            }
        }

        total += odd * oddFull + even * evenFull;
        return total;
    }
}
