import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class PartTwo {
    private static final String puzzleInput = "2023/Day04/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);

        int numCards = lines.size();
        int[] wins = new int[numCards];
        int[] copies = new int[numCards];
        for (int i = 0; i < numCards; i++) {
            copies[i] = 1;
        }
        calculateWins(lines, wins);
        calculateCopies(wins, copies);
        int result = sumCopies(copies);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static void calculateWins(List<String> lines, int[] wins) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            String[] card = line.split(": ")[1].split(" \\| ");
            int[] winNums = Arrays.stream(card[0].trim().split(" +"))
                .mapToInt(Integer::parseInt)
                .toArray();
            int[] ownNums = Arrays.stream(card[1].trim().split(" +"))
                .mapToInt(Integer::parseInt)
                .toArray();
            for (int o : ownNums) {
                for (int w : winNums) {
                    if (o == w) {
                        wins[i]++;
                        break;
                    }
                }
            }
        }
    }

    public static void calculateCopies(int[] wins, int[] copies) {
        int size = wins.length;
        for (int i = 0; i < size; i++) {
            int win = wins[i];
            for (int j = i + 1; j < Math.min(i + win + 1, size); j++) {
                copies[j] += copies[i];
            }
        }
    }

    public static int sumCopies(int[] copies) {
        int totalCopies = 0;
        for (int copy : copies) {
            totalCopies += copy;
        }
        return totalCopies;
    }
}
