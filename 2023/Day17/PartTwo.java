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

    public static int manhattenDistance(int[] thisLoc, int[] max) {
        return max[0] - thisLoc[0] + max[1] - thisLoc[1];
    }

    public static int[] getNewLoc(int direction, int[] loc, int[] max) {
        int x = loc[1];
        int y = loc[0];
        switch (direction) {
            case 0 -> y--;
            case 1 -> x++;
            case 2 -> y++;
            case 3 -> x--;
        }
        if (x >= 0 && x < max[1] && y >= 0 && y < max[0]) {
            return new int[] {y, x};
        }
        return null;
    }

    public static void addToQueue(PriorityQueue<QueueElement> pq, int[][] heatLoss,
                                  boolean[][][][] visited, int[] loc, int direction, int steps, QueueElement current) {
        int[] max = new int[] {heatLoss.length, heatLoss[0].length};
        int[] end = new int[] {max[0] - 1, max[1] - 1};
        int[] newLoc = getNewLoc(direction, loc, max);
        if (newLoc != null) {
            // don't visit if visited
            int hl = heatLoss[newLoc[0]][newLoc[1]];
            if (!visited[newLoc[0]][newLoc[1]][direction][steps]) {
                pq.add(new QueueElement(newLoc, direction, steps, current.distance + hl,
                    hl + manhattenDistance(newLoc, end)));
            }
        }
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
            heatLoss[0][0] + manhattenDistance(new int[] {0, 0}, end));
        pq.add(start);
        start = new QueueElement(new int[] {0, 0}, 2, 0, 0,
            heatLoss[0][0] + manhattenDistance(new int[] {0, 0}, end));
        pq.add(start);

        QueueElement current = pq.poll();
        while (current != null && !(manhattenDistance(current.location, end) == 0 && current.steps >= 4)) {
            int[] loc = current.location;
            visited[loc[0]][loc[1]][current.direction][current.steps] = true;

            // Add left
            if (current.steps < 4) {
                // Front only
                addToQueue(pq, heatLoss, visited, loc, current.direction, current.steps + 1, current);
            } else if (current.steps < 10) {
                // All 3 directions
                addToQueue(pq, heatLoss, visited, loc, current.direction, current.steps + 1, current);
                addToQueue(pq, heatLoss, visited, loc, (current.direction + 3) % 4, 1, current);
                addToQueue(pq, heatLoss, visited, loc, (current.direction + 1) % 4, 1, current);
            } else {
                addToQueue(pq, heatLoss, visited, loc, (current.direction + 3) % 4, 1, current);
                addToQueue(pq, heatLoss, visited, loc, (current.direction + 1) % 4, 1, current);
            }

            System.out.println(current.distance);
            current = pq.poll();
        }

//        System.out.println(current.steps);
        return current.distance;
    }
}
