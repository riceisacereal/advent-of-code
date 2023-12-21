import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PartOne {
    private static final String puzzleInput = "2023/Day21/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
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

    public static int parseInput(List<String> lines) {
        final int maxY = lines.size();
        final int maxX = lines.get(0).length();
        final int RANGE = 64;

        int start = RANGE % 2;
        int[] s = new int[2];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if (lines.get(i).charAt(j) == 'S') {
                    s[0] = i;
                    s[1] = j;
                    break;
                }
            }
        }

        boolean[][] reachable = new boolean[maxY][maxX];
        boolean[][] stepMap = new boolean[maxY][maxX];
        markReachable(lines, reachable, s, RANGE);

        for (int i = s[0] - RANGE; i <= s[0] + RANGE; i++) {
            int rangeX = RANGE - Math.abs(i - s[0]);
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

        return points;
    }
}
