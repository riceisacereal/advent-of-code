import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

// 24240 too high

public class PartOne {
    private static final String puzzleInput = "2023/Day04/input.txt";

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
        for (String line : lines) {
            int[][] intArray = Shared.getIntArray(line);
            int[] winNums = intArray[0];
            int[] ownNums = intArray[1];

            int count = 0;
            for (int o : ownNums) {
                for (int w : winNums) {
                    if (Objects.equals(w, o)) {
                        count++;
                    }
                }
            }
            if (count > 0) points += (int) Math.pow(2, count - 1);
        }
        return points;
    }
}
