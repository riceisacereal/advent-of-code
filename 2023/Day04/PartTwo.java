import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class PartTwo {
    private static int[] wins;
    private static int[] copies;

    public static void main(String[] args) throws IOException {
        String puzzleInput = "2023/Day04/input.txt";
        partTwo(puzzleInput);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int parseInput(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] card = line.split(": ")[1].split(" \\| ");
            String[] winNums = card[0].trim().split(" +");
            String[] ownNums = card[1].trim().split(" +");
            for (String o : ownNums) {
                for (String w : winNums) {
                    if (Objects.equals(w, o)) { // CHANGE TO INT
                        wins[i]++;
                    }
                }
            }
        }

        for (int i = 0; i < lines.size(); i++) {
            int win = wins[i];
            for (int j = i + 1; j < Math.min(i + win + 1, lines.size()); j++) {
                copies[j] += copies[i];
            }
        }

        int totalCopies = 0;
        for (int i = 0; i < lines.size(); i++) {
            System.out.println(copies[i]);
            totalCopies += copies[i];
        }
        return totalCopies;
    }

    public static void partTwo(String puzzleInput) throws IOException {
        List<String> lines = readFile(puzzleInput);
        wins = new int[lines.size()];
        copies = new int[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            copies[i] = 1;
        }
        int result = parseInput(lines);
        System.out.println(result);
    }
}
