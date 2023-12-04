import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

// 24240 too high

public class PartOne {
    public static void main(String[] args) throws IOException {
        String puzzleInput = "2023/Day04/input.txt";
        partOne(puzzleInput);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int parseInput(List<String> lines) {
        int points = 0;
        for (String line : lines) {
            String[] card = line.split(": ")[1].split(" \\| ");
            String[] winNums = card[0].trim().split(" +");
            String[] ownNums = card[1].trim().split(" +");
            int count = 0;
            for (String o : ownNums) {
                for (String w : winNums) {
                    if (Objects.equals(w, o)) { // CHANGE TO INT
                        count++;
                        System.out.print(w + " ");
                    }
                }
            }
            if (count > 0) {
                int point = (int) Math.pow(2, count - 1);
                System.out.println(point);
                points += point;
            }
        }
        return points;
    }

    public static void partOne(String puzzleInput) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }
}
