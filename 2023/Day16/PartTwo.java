import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PartTwo {
    private static final String puzzleInput = "2023/Day16/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int parseInput(List<String> lines) {
        int maxX = lines.get(0).length();
        int maxY = lines.size();

        int maxEnergy = 0;
        int direction = 2;
        for (int row = 0; row < maxY; row += maxY - 1) {
            for (int col = 0; col < maxX; col++) {
                maxEnergy = Math.max(maxEnergy, Shared.reflectBeams(lines, new Beam(new int[] {row, col}, direction))); //
            }
            direction = 0;
        }

        direction = 1;
        for (int col = 0; col < maxX; col += maxX - 1) {
            for (int row = 0; row < maxY; row++) {
                maxEnergy = Math.max(maxEnergy, Shared.reflectBeams(lines, new Beam(new int[] {row, col}, direction))); //
            }
            direction = 3;
        }

        return maxEnergy;
    }
}
