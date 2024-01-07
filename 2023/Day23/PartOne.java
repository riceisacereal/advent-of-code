import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PartOne {
    private static final String puzzleInput = "2023/Day23/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int parseInput(List<String> lines) {
        int maxY = lines.size();
        int maxX = lines.get(0).length();
        int[][] maxDist = new int[maxY][maxX];

        int[] start = new int[] {0, 1};
        int[] end = new int[] {maxY - 1, maxX - 2};
        PriorityQueue<Head> q = new PriorityQueue<>(Collections.reverseOrder(Comparator.comparingInt(a -> a.distance)));
        q.add(new Head(start, 0, maxY, maxX, new boolean[maxY][maxX]));

        while (!q.isEmpty()) {
            Head current = q.poll();
            int[] loc = current.coord;

            ArrayList<int[]> displace = new ArrayList<>();
            switch (lines.get(loc[0]).charAt(loc[1])) {
                case '<' -> displace.add(new int[] {0, -1});
                case '>' -> displace.add(new int[] {0, 1});
                case '^' -> displace.add(new int[] {-1, 0});
                case 'v' -> displace.add(new int[] {1, 0});
                default -> {
                    displace.add(new int[] {0, -1});
                    displace.add(new int[] {0, 1});
                    displace.add(new int[] {-1, 0});
                    displace.add(new int[] {1, 0});
                }
            }

            for (int[] d : displace) {
                int y = loc[0] + d[0];
                int x = loc[1] + d[1];
                if (x >= 0 && x < maxX && y >= 0 && y < maxY) {
                    int newDistance = current.distance + 1;
                    int[] newLoc = new int[] {y, x};
                    if (lines.get(y).charAt(x) != '#' && !current.visited(newLoc) && newDistance >= maxDist[y][x]) {
                        maxDist[y][x] = newDistance;
                        Head newHead = new Head(newLoc, newDistance, maxY, maxX, current.visited);
                        q.add(newHead);
                    }
                }
            }
        }

        return maxDist[end[0]][end[1]];
    }
}
