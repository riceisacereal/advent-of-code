import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PartOne {
    private static final String puzzleInput = "2023/Day21/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int parseInput(List<String> lines) {
        final int maxY = lines.size();
        final int maxX = lines.get(0).length();
        final int RANGE = 64;

        int[] s = new int[2];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if (lines.get(i).charAt(j) == 'S') {
                    s[0] = i;
                    s[1] = j;
                    break;
                }
            }
        }

        return Shared.countReachable(lines, RANGE, s);
    }
}
