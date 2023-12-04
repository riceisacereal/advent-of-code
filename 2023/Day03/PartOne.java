import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

// 505664 too high
// 497027 too low

public class PartOne {
    private static final String puzzleInput = "2023/Day03/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static Boolean isSymbol(int c) {
        return c != 46 && (c <= 47 || c >= 58);
    }

    public static Boolean isNumber(int c) {
        return (c >= 48 && c <= 57);
    }

    public static Boolean checkInclusion(List<String> lines, int num, int level, int start, int end) {
        int maxY = lines.size();
        int maxX = lines.get(0).length();
        for (int y = Math.max(level - 1, 0); y < Math.min(level + 2, maxY); y++) {
            for (int x = Math.max(start - 1, 0); x < Math.min(end + 1, maxX); x++) {
                int c = lines.get(y).charAt(x);
                if (isSymbol(c)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int parseInput(List<String> lines) {
        int sumPartNum = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (!isNumber(c)) {
                    continue;
                }

                int start = j;
                for (; j < line.length(); j++) {
                    c = line.charAt(j);
                    if (c == '.' || isSymbol(c)) {
                        int num = Integer.parseInt(line.substring(start, j));
                        if (checkInclusion(lines, num, i, start, j)) sumPartNum += num;
                        break;
                    }
                    if (j + 1 == line.length()) {
                        int num = Integer.parseInt(line.substring(start));
                        if (checkInclusion(lines, num, i, start, line.length())) sumPartNum += num;
                    }
                }
            }
        }
        return sumPartNum;
    }
}
