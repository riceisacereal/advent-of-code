import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    private static final String puzzleInput = "2023/Day13/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static boolean checkReflection(ArrayList<char[]> pattern, int start, int max, int ray, boolean row) {
        int low = start;
        int high = start + 1;

        while (low >= 0 && high < max) {
            if (row && pattern.get(ray)[low] != pattern.get(ray)[high]) return false;
            else if (!row && pattern.get(low)[ray] != pattern.get(high)[ray]) return false;
            low--; high++;
        }
        return true;
    }

    public static int getResult(ArrayList<ArrayList<char[]>> allPatterns) {
        int result = 0;

        for (ArrayList<char[]> pattern : allPatterns) {
            int maxY = pattern.size();
            int maxX = pattern.get(0).length;

            HashMap<String, Integer> count = new HashMap<>();
            for (int i = 0; i < maxX; i++) {
                for (int j = 0; j < maxY; j++) {
                    // check left-right reflection
                    if (checkReflection(pattern, i, maxX, j, true)) {
                        count.merge("col" + i, 1, Integer::sum);
                    }

//                    int left = i;
//                    int right = i + 1;
//                    count.merge("col" + i, 1, Integer::sum);
//                    while (left >= 0 && right < maxX) {
//                        if (pattern.get(j)[left] != pattern.get(j)[right]) {
//                            count.put("col" + i, count.get("col" + i) - 1);
//                            break;
//                        }
//                        left--;
//                        right++;
//                    }

                    // check up-down reflection when not last row
                    if (checkReflection(pattern, j, maxY, i, false)) {
                        count.merge("row" + j, 1, Integer::sum);
                    }

//                    int up = j;
//                    int down = j + 1;
//                    count.merge("row" + j, 1, Integer::sum);
//                    while (up >= 0 && down < maxY) {
//                        if (pattern.get(up)[i] != pattern.get(down)[i]) {
//                            count.put("row" + j, count.get("row" + j) - 1);
//                            break;
//                        }
//                        up--;
//                        down++;
//                    }
                }
            }

            // check all row axis
            for (int i = 0; i < maxY - 1; i++) {
                if (count.get("row" + i) != null && count.get("row" + i) == maxX - 1) { // Remove -1 for part 1 answer
                    result += 100 *(i + 1);
                }
            }

            // check all column axis
            for (int i = 0; i < maxX - 1; i++) {
                if (count.get("col" + i) != null && count.get("col" + i) == maxY - 1) { // Remove -1 for part 1 answer
                    result += i + 1;
                }
            }
        }

        return result;
    }

    public static int parseInput(List<String> lines) {
        ArrayList<ArrayList<char[]>> allPatterns = new ArrayList<>();
        ArrayList<char[]> pattern = new ArrayList<>();
        for (String line : lines) {
            if (line.isBlank()) { // If line is only new line
                allPatterns.add(pattern);
                pattern = new ArrayList<>();
                continue;
            }
            pattern.add(line.toCharArray());
        }
        allPatterns.add(pattern);

        return getResult(allPatterns);
    }
}
