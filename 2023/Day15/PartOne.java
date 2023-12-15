import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PartOne {
    private static final String puzzleInput = "2023/Day15/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int parseInput(List<String> lines) {
        int points = 0;
        for (String line : lines.get(0).split(",")) {
            int hash = 0;
            for (char c : line.toCharArray()) {
                hash = ((hash + c) * 17) % 256;
            }
            points += hash;
        }
        return points;
    }
}
