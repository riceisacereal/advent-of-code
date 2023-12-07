import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String puzzleInput = "2023/Day07/input.txt";

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
        ArrayList<CamelCard> allCards = new ArrayList<>();
        for (String line : lines) {
            allCards.add(new CamelCard(line, 2)); // Change which part to run for here
        }
        allCards.sort(CamelCard::compareTo);

        int size = allCards.size();
        for (int i = size; i >= 1; i--) {
            points += i * allCards.get(size - i).getBid();
        }
        return points;
    }
}
