import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PartTwo {
    private static final String puzzleInput = "2023/Day03/input.txt";
    private static int[][] gears;

    public static void main(String[] args) throws Exception {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static Boolean isNumber(int c) {
        return (c >= 48 && c <= 57);
    }

    public static Boolean isSymbol(int c) {
        return c != 46 && (c <= 47 || c >= 58);
    }

    public static int checkSecondNumber(int y, int x, int num) throws Exception {
        int existing = gears[y][x];
        if (existing == 0) {
            // If no numbers have been found for that gear yet,
            // place number in the coordinates of the gear
            gears[y][x] = num;
            return 0;
        } else if (existing > 0) {
            // If a number has already been found,
            // store a negative number, so we can catch an error
            gears[y][x] = -(existing * num);
            return existing * num;
        } else {
            // Catch when more than 2 numbers are adjacent to a gear
            // This never triggered though, so that's good
            // Otherwise I would have had to rewrite a lot of code
            throw new Exception("More than two adjacent detected");
        }
    }

    public static int checkInclusion(List<String> lines, int level, int start, int end) throws Exception {
        String line = lines.get(level);
        int maxX = line.length();
        int maxY = lines.size();
        int num = Integer.parseInt(line.substring(start, end));

        for (int y = Math.max(level - 1, 0); y < Math.min(level + 2, maxY); y++) {
            for (int x = Math.max(start - 1, 0); x < Math.min(end + 1, maxX); x++) {
                int c = lines.get(y).charAt(x);
                if (c == 42) {
                    // If there is a gear around the number
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
                // Find next number
                char c = line.charAt(j);
                if (!isNumber(c)) {
                    continue;
                }

                int start = j;
                // Identify length of number
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
        return sumPartNum;
    }
}
