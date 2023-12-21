import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 10321330383883446986 too high
// 10321329602941223166 too high
// 602219006907038 too high
// 601441080564338 not right
// 601441063166538

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

    public static int manhattanDistance(int[] s, int[] p) {
        return Math.abs(s[0] - p[0]) + Math.abs(s[1] - p[1]);
    }

    public static void markReachable(List<String> lines, boolean[][] reachable, int[] s, final int RANGE) {
        int maxY = reachable.length;
        int maxX = reachable[0].length;
        boolean[][] visited = new boolean[maxY][maxX];
        Queue<int[]> paths = new LinkedList<>();
        paths.add(s);

        for (int step = 0; step <= RANGE; step++) {
            Queue<int[]> newPaths = new LinkedList<>();
            while (!paths.isEmpty()) {
                int[] cp = paths.poll();
                visited[cp[0]][cp[1]] = true;
                reachable[cp[0]][cp[1]] = true;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if ((i + j) % 2 == 0) {
                            continue;
                        }

                        int y = cp[0] + i;
                        int x = cp[1] + j;
                        if (y >= 0 && y < maxY && x >= 0 && x < maxY) {
                            if (!visited[y][x] && lines.get(y).charAt(x) != '#') {
                                newPaths.add(new int[] {y, x});
                                visited[y][x] = true;
                            }
                        }
                    }
                }
            }
            paths = newPaths;
        }
    }

    public static long countValid(List<String> lines, int range, int[] s) {
        int maxY = lines.size();
        int maxX = lines.get(0).length();

        int start = range % 2;

        boolean[][] reachable = new boolean[maxY][maxX];
        boolean[][] stepMap = new boolean[maxY][maxX];
        markReachable(lines, reachable, s, range);

        for (int i = s[0] - range; i <= s[0] + range; i++) {
            int rangeX = range - Math.abs(i - s[0]);
            for (int j = s[1] - rangeX; j <= s[1] + rangeX; j++) {
                int[] p = new int[] {i % maxY, j % maxX};
                if (p[0] < 0) p[0] = maxY + p[0];
                if (p[1] < 0) p[1] = maxX + p[1];
                if (manhattanDistance(s, p) % 2 == start && lines.get(p[0]).charAt(p[1]) != '#' && reachable[p[0]][p[1]]) {
                    stepMap[p[0]][p[1]] = true;
                }
            }
        }

//        for (int i = 0; i < maxY; i++) {
//            for (int j = 0; j < maxX; j++) {
//                if (stepMap[i][j]) {
//                    System.out.print('O');
//                } else {
//                    System.out.print(lines.get(i).charAt(j));
//                }
//            }
//            System.out.println();
//        }

        int points = 0;
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if (stepMap[i][j]) {
                    points++;
                }
            }
        }

        System.out.println(points);
        return points;
    }

    public static long parseInput(List<String> lines) {
        final int maxY = lines.size();
        final int maxX = lines.get(0).length();
        final int STEPS = 26501365; // 26501365
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
            total += countValid(lines, range, new int[] {coord[0], coord[1]});
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
            small += countValid(lines, range, new int[] {coord[0], coord[1]});
        }
        total += (long) small * N;

        range = 64 + 131; // big triangles are odd
        int big = 0;
        // calculate big triangles
        for (int[] coord : coords) {
            big += countValid(lines, range, new int[] {coord[0], coord[1]});
        }
        total += (long) big * (N - 1);

        // odd full
        range = 201; // big number just in case
        long oddFull = countValid(lines, range, new int[] {maxY / 2, maxX / 2});

        // even full
        range = 200; // big number just in case
        long evenFull = countValid(lines, range, new int[] {maxY / 2, maxX / 2});

        // count even and odd
        // starts with 1 odd in the middle
        // then goes 4e, 8o, 12e, 16o
        long odd = 1;
        long even = 0;
        for (int i = 1; i < 202300; i++) { // don't count outer odd border
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
