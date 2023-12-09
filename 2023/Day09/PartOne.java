import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PartOne {
    private static final String puzzleInput = "2023/Day09/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        long result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static Boolean allZeros(List<Long> history) {
        for (Long l : history) {
            if (l != 0) return false;
        }
        return true;
    }

    public static long getPrediction(List<Long> history) {
        if (allZeros(history)) return 0;

        ArrayList<Long> differences = new ArrayList<>();
        for (int i = 0; i < history.size() - 1; i++) {
            differences.add(history.get(i + 1) - history.get(i));
        }
        return history.get(history.size() - 1) + getPrediction(differences);
    }

    public static long parseInput(List<String> lines) {
        long points = 0;
        for (String line : lines) {
            points += getPrediction(Arrays.stream(line.split(" "))
                .map(Long::parseLong).collect(Collectors.toList()));
        }
        return points;
    }
}
