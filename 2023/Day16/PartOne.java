import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PartOne {
    static class Beam {
        int[] headCoord;
        int direction;

        public Beam(int[] coord, int direction) {
            this.headCoord = coord;
            this.direction = direction;
        }

        public ArrayList<Beam> checkTile(List<String> lines) {
            int x = headCoord[1];
            int y = headCoord[0];

            // Check new direction and if split
            // return this; // Successfully traveled
            // return new Beam; // Split
            // return null; // Hit edge
            ArrayList<Beam> newBeams = new ArrayList<>();
            switch (lines.get(y).charAt(x)) {
                case '\\' -> {
                    // Reflect
                    // 0 -> 3
                    // 1 -> 2
                    // 2 -> 1
                    // 3 -> 0
                    direction = 3 - direction;
                    newBeams.add(this);
                }
                case '/' -> {
                    // Reflect
                    // 0 -> 1
                    // 1 -> 0
                    // 2 -> 3
                    // 3 -> 2
                    direction = (2 * (direction / 2)) + (direction + 1) % 2;
                    newBeams.add(this);
                }
                case '-' -> {
                    if (direction == 1 || direction == 3) {
                        newBeams.add(this);
                    } else {
                        // Else split
                        direction = 1;
                        newBeams.add(this);
                        newBeams.add(new Beam(new int[] {headCoord[0], headCoord[1]}, 3));
                    }
                }
                case '|' -> {
                    if (direction == 0 || direction == 2) {
                        newBeams.add(this);
                    } else {
                        // Else split
                        direction = 0;
                        newBeams.add(this);
                        newBeams.add(new Beam(new int[] {headCoord[0], headCoord[1]}, 2));
                    }
                }
                default -> newBeams.add(this);
            }
            return newBeams;
        }

        public Beam doUniqueTravel(List<String> lines, int[][][] energized) {
            int maxX = lines.get(0).length();
            int maxY = lines.size();
            int x = headCoord[1];
            int y = headCoord[0];

            // Assign new coordinates
            switch (direction) {
                case 0 -> {
                    if (y == 0) return null;
                    headCoord[0]--;
                }
                case 1 -> {
                    if (x == maxX - 1) return null;
                    headCoord[1]++;
                }
                case 2 -> {
                    if (y == maxY - 1) return null;
                    headCoord[0]++;
                }
                case 3 -> {
                    if (x == 0) return null;
                    headCoord[1]--;
                }
            }
            // If this direction on this coordinate has already been traveled
            if (energized[headCoord[0]][headCoord[1]][direction] == 1) return null;
            return this;
        }

        public ArrayList<Beam> next(List<String> lines, int[][][] energized) {
            ArrayList<Beam> newBeams = new ArrayList<>();
            for (Beam b : this.checkTile(lines)) {
                Beam newB = b.doUniqueTravel(lines, energized);
                if (newB != null) newBeams.add(newB);
            }
            return newBeams;
        }
    }

    private static final String puzzleInput = "2023/Day16/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static void markEnergized(int[][][] energized, Stack<Beam> beams) {
        for (Beam b : beams) {
            energized[b.headCoord[0]][b.headCoord[1]][b.direction] = 1;
        }
    }

    public static int parseInput(List<String> lines) {
        int maxX = lines.get(0).length();
        int maxY = lines.size();

        // Make int array to mark energized blocks
        int[][][] energized = new int[maxY][maxX][4];

        Stack<Beam> beams = new Stack<>();
        beams.push(new Beam(new int[] {0, 0}, 1));
        while (!beams.isEmpty()) {
            markEnergized(energized, beams);

            Stack<Beam> newBeams = new Stack<>();
            while (!beams.isEmpty()) {
                Beam b = beams.pop();
                for (Beam newB : b.next(lines, energized)) {
                    newBeams.push(newB);
                }
            }
            beams = newBeams;
        }

        int count = 0;
        for (int[][] i : energized) {
            for (int[] j : i) {
                for (int k : j) {
                    if (k == 1) {
                        count++;
                        break;
                    }
                }
            }
        }
        return count;
    }
}
