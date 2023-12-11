import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PartOne {
    private static final String puzzleInput = "2023/Day11/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        long result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static boolean allEmptySpace(List<String> lines, int col) {
        for (String s : lines) {
            if (s.charAt(col) == '#') return false;
        }
        return true;
    }

    public static long parseInput(List<String> lines) {
        final int EXPAND = 1000000 - 1; // Change this number to toggle between part 1 and 2
        int rows = lines.size();
        int cols = lines.get(0).length();

        ArrayList<int[]> galaxyCoordinates = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char c = lines.get(row).charAt(col);
                if (c == '#') {
                    galaxyCoordinates.add(new int[] {row, col});
                }
            }
        }

        int rowOffset = 0;
        for (int row = 0; row < rows; row++) {
            int newIndex = row + rowOffset;
            if (lines.get(row).replaceAll("#", "").length() == cols) {
                for (int[] co : galaxyCoordinates) {
                    if (co[0] > newIndex) {
                        co[0] += EXPAND;
                    }
                }
                rowOffset += EXPAND;
            }
        }

        int colOffset = 0;
        for (int col = 0; col < cols; col++) {
            int newIndex = col + colOffset;
            if (allEmptySpace(lines, col)) {
                for (int[] co : galaxyCoordinates) {
                    if (co[1] > newIndex) {
                        co[1] += EXPAND;
                    }
                }
                colOffset += EXPAND;
            }
        }

        long distances = 0;
        for (int n = 0; n < galaxyCoordinates.size(); n++) {
            if (n == galaxyCoordinates.size() - 1) {
                break;
            }

            int[] thisGalaxy = galaxyCoordinates.get(n);
            for (int next = n + 1; next < galaxyCoordinates.size(); next++) {
                int[] nextGalaxy = galaxyCoordinates.get(next);
                distances += Math.abs(thisGalaxy[0] - nextGalaxy[0])
                    + Math.abs(thisGalaxy[1] - nextGalaxy[1]);
            }
        }

        return distances;
    }
}