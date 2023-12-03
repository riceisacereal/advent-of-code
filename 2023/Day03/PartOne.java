import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

// 505664 too high
// 497027 too low

public class PartOne {
    public static void main(String[] args) throws IOException {
        String puzzleInput = "2023/Day03/input.txt";
        partOne(puzzleInput);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static Boolean isSymbol(int c) {
        if (c == 46) {
            return false;
        } else return (c > 32 && c < 48) || (c > 57 && c < 65) || (c > 90 && c < 97) ||
            (c > 122 && c < 127);
    }

    public static int checkInclusion(List<String> lines, String line, int level, int start, int end,
                                     int maxY, int maxX) {
        int num = Integer.parseInt(line.substring(start, end));
        for (int y = Math.max(level - 1, 0); y < Math.min(level + 2, maxY); y++) {
            for (int x = Math.max(start - 1, 0); x < Math.min(end + 1, maxX); x++) {
                int c = lines.get(y).charAt(x);
                if (isSymbol(c)) {
                    System.out.println(num);
                    return num;
                }
            }
        }
        return 0;
    }

    public static int parseInput(List<String> lines) {
        int sumPartNum = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if ((int) c > 47 && (int) c < 58) {
                    int start = j;
                    for (; j < line.length(); j++) {
                        c = line.charAt(j);
                        if (c == '.' || isSymbol(c)) {
                            sumPartNum += checkInclusion(lines, line, i, start, j, lines.size(),
                                line.length());
                            break;
                        }
                        if (j + 1 == line.length()) {
                            sumPartNum += checkInclusion(lines, line, i, start, line.length(),
                                lines.size(), line.length());
                        }
                    }
                }
            }
        }
        return sumPartNum;
    }

    public static void partOne(String puzzleInput) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }
}
