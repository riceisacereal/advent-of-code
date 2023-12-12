import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

// 8620 too high

public class PartOne {
    private static final String puzzleInput = "2023/Day12/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static boolean checkArrangement(char[] board, int groupStart, int groupSize,
                                           int spaceStart, boolean lastElement) {
        // Check dots before
        for (int j = spaceStart; j < groupStart; j++) {
            // Check if the '.' works
            char onBoard = board[j];
            if (onBoard == '#') {
                // Does not work
                return false;
            }
        }

        for (int j = groupStart; j < groupStart + groupSize; j++) {
            // Check if the '#' works
            char onBoard = board[j];
            if (onBoard == '.') {
                // Does not work
                return false;
            }
        }

        // Check dot after
        // At the end already no need to check
        if (groupStart + groupSize >= board.length) return true;
        // Check dot right after if not last element
        if (board[groupStart + groupSize] == '#') return false;
        // If last element, check all remaining dots
        if (lastElement) {
            for (int j = groupStart + groupSize; j < board.length; j++) {
                // Check if the '.' works
                char onBoard = board[j];
                if (onBoard == '#') {
                    // Does not work
                    return false;
                }
            }
        }

        return true;
    }

    public static int getNextArrangements(char[] board, int groupIndex, int[] groupNums,
                                          int spaceStart) {
        // 0 1 2 3 4 5 6 7
        // ? ? ? ? ? . # #
        // If last element already reached, return 1, since this arrangement has worked all the way
        if (groupIndex == groupNums.length) return 1;

        int groupSize = groupNums[groupIndex];
        // If element can't even fit, return 0
        if (board.length - spaceStart < groupSize) return 0;

        int spaceEnd = board.length; // End is exclusive
        for (int i = groupNums.length - 1; i > groupIndex; i--) {
            spaceEnd -= groupNums[i] + 1;
        }

        int count = 0;
        for (int i = spaceStart; i <= spaceEnd - groupSize; i++) {
            // Try out all arrangements with the current group
            if (checkArrangement(board, i, groupSize, spaceStart, groupIndex == groupNums.length - 1)) {
                // Check current arrangement with board
                // If current arrangement works += recursively find how many next arrangements work
                count += getNextArrangements(board, groupIndex + 1, groupNums, i + groupSize + 1);
            }
        }
        return count;


    }

    public static int parseInput(List<String> lines) {
        //Identify existing #
        //Whip up all possibilities of a row:
        //
        //AND them with the given array - if true, count towards possibilities
        //i=0 until i<
        //
        //Count possible arrangements
        //When 5 (7-1-1) space, 4 ways to arrange the 2: 0,1,2,3 spaces on the left (space - size + 1?
        //When 4 (7-2-1) spaces, 4 ways to arrange the 1

        int points = 0;
        for (String line : lines) {
            String[] picross = line.split(" ");
            char[] board = picross[0].trim().strip().toCharArray();
            int[] groupNums = Arrays.stream(picross[1].trim().split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
            int possibleArrangements = getNextArrangements(board, 0, groupNums, 0);
            System.out.println(possibleArrangements);
            points += possibleArrangements;
        }
        return points;
    }
}
