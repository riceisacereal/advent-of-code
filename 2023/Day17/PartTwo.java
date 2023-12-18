import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

// 810 too high

public class PartTwo {
    private static final String puzzleInput = "2023/Day17/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int parseInput(List<String> lines) {
        final int maxY = lines.size();
        final int maxX = lines.get(0).length();
        final int[] end = new int[] {maxY - 1, maxX - 1};
        final int[][] heatLoss = new int[maxY][maxX];
        boolean[][][][] visited = new boolean[maxY][maxX][4][11];
        for (int i = 0; i < maxY; i++) {
            String line = lines.get(i);
            for (int j = 0; j < maxX; j++) {
                heatLoss[i][j] = line.charAt(j) - 48;
            }
        }

        PriorityQueue<QueueElement> pq = new PriorityQueue<>(
            Comparator.comparingInt(a -> a.heuristicValue + a.distance));
        QueueElement start = new QueueElement(new int[] {0, 0}, 1, 0, 0,
            heatLoss[0][0] + Shared.manhattenDistance(new int[] {0, 0}, end));
        pq.add(start);
        start = new QueueElement(new int[] {0, 0}, 2, 0, 0,
            heatLoss[0][0] + Shared.manhattenDistance(new int[] {0, 0}, end));
        pq.add(start);

        QueueElement current = pq.poll();
        while (current != null && !(Shared.manhattenDistance(current.location, end) == 0 && current.steps >= 4)) {
            int[] loc = current.location;
            visited[loc[0]][loc[1]][current.direction][current.steps] = true;

            // Add left
            if (current.steps < 4) {
                // Ahead only
                Shared.addToQueue(pq, heatLoss, visited, loc, current.direction, current.steps + 1, current);
            } else if (current.steps < 10) {
                // All 3 directions
                Shared.addToQueue(pq, heatLoss, visited, loc, current.direction, current.steps + 1, current);
                Shared.addToQueue(pq, heatLoss, visited, loc, (current.direction + 3) % 4, 1, current);
                Shared.addToQueue(pq, heatLoss, visited, loc, (current.direction + 1) % 4, 1, current);
            } else {
                // Turn
                Shared.addToQueue(pq, heatLoss, visited, loc, (current.direction + 3) % 4, 1, current);
                Shared.addToQueue(pq, heatLoss, visited, loc, (current.direction + 1) % 4, 1, current);
            }

            current = pq.poll();
        }

        if (current == null) return -1;
        return current.distance;
    }
}
