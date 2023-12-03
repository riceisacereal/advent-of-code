import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PartTwo {
    private static int[][] gears;

    public static void main(String[] args) throws Exception {
        String puzzleInput = "2023/Day03/input.txt";
        partOne(puzzleInput);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static Boolean isNumeric(int n) {
        return (n > 47 && n < 58);
    }

    public static Boolean isSymbol(int c) {
        if (c == 46) {
            return false;
        } else return (c > 32 && c < 48) || (c > 57 && c < 65) || (c > 90 && c < 97) ||
            (c > 122 && c < 127);
    }

    public static int checkSecondNumber(int y, int x, int num)
        throws Exception {
        int existing = gears[y][x];
        if (existing == 0) {
            gears[y][x] = num;
            return 0;
        } else if (existing > 0) {
            gears[y][x] = -(existing * num);
            return existing * num;
        } else {
            throw new Exception("More than two adjacent detected");
//            return existing;
        }
    }

    public static int checkInclusion(List<String> lines, int level, int start, int end)
        throws Exception {
        String line = lines.get(level);
        int maxX = line.length();
        int maxY = lines.size();

        int num = Integer.parseInt(lines.get(level).substring(start, end));
        for (int y = Math.max(level - 1, 0); y < Math.min(level + 2, maxY); y++) {
            for (int x = Math.max(start - 1, 0); x < Math.min(end + 1, maxX); x++) {
                int c = lines.get(y).charAt(x);
                if (c == 42) {
                    return checkSecondNumber(y, x, num);
                }
            }
        }
        return 0;
    }

    public static int parseInput(List<String> lines) throws Exception {
        gears = new int[lines.size()][lines.get(0).length()];

        int sumPartNum = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (isNumeric(c)) {
                    int start = j;
                    for (; j < line.length(); j++) {
                        c = line.charAt(j);
                        if (c == '.' || isSymbol(c)) {
                            sumPartNum += checkInclusion(lines, i, start, j);
                            break;
                        }
                        if (j + 1 == line.length()) {
                            sumPartNum += checkInclusion(lines, i, start, line.length());
                        }
                    }
                }
            }
        }
        return sumPartNum;
    }

    public static void partOne(String puzzleInput) throws Exception {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }
}
