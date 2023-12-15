import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PartOne {
    private static final String puzzleInput = "2023/Day14/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int parseInput(List<String> lines) {
        int maxY = lines.size();
        int maxX = lines.get(0).length();

        int[] track = new int[maxX];
        // Each index keeps track of the earliest position possible:
        int totalLoad = 0;
        for (int i = 0; i < maxY; i++) {
            char[] line = lines.get(i).toCharArray();
            for (int j = 0; j < maxX; j++) {
                char c = line[j];
                if (c == '#') track[j] = i + 1;
                else if (c == 'O') {
                    totalLoad += maxY - track[j];
                    track[j]++;
                }
            }
        }
        return totalLoad;
    }
}