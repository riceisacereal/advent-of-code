import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class PartTwo {
    private static final String puzzleInput = "2023/Day12/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        long result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static boolean doesNotContain(char[] board, int start, int end, char unwantedChar) {
        if (start >= board.length) return true;

        for (int i = start; i < end; i++) {
            if (board[i] == unwantedChar) return false;
        }
        return true;
    }

    public static int getStart(int[] groupNums, int index) {
        int start = 0;
        for (int i = 0; i < index; i++) {
            start += groupNums[i] + 1;
        }
        return start;
    }

    public static int getEnd(int[] groupNums, int index, int maxLength) {
        int end = maxLength; // End is exclusive 0 1 2 3 4 5 6
        for (int i = groupNums.length - 1; i > index; i--) {
            end -= groupNums[i] + 1;
        }
        return end;
    }

    public static long checkTrailingDotsGetMemoi(long[][] mem, char[] board, int start, int end, int elementIndex) {
        // Check possible arrangements after this = find the max range that can be dots
        long sum = 0;
        for (int di = start; di <= end; di++) {
            if (doesNotContain(board, start, Math.min(di + 1, board.length), '#')) {
                sum += mem[di + 1][elementIndex];
            } else {
                // Break when '#' is hit
                break;
            }
        }
        return sum;
    }

    public static long doDynamicProgramming(char[] board, int[] groupNums) {
        // Make two-dimensional array of board * groupNums
        long[][] mem = new long[board.length + 2][groupNums.length + 1];

        // Work from back to front
        // Determine for each groupNum starting at each board position
        // 1. Whether itself can be placed there
        // 2. Sum up the possible arrangements after it by: spacing out one dot, then two, then tree...
        //    (the added value would be mem[bi + (size - 1) + dots + 1][gi + 1])
        //    Stop when a '#' is hit
        //    Put the sum of all these values in mem[bi][gi]

        // Last element is special case where if it fits itself, mem[bi][gi] = 1;
        // and also needs to check all the spaces after it
        int start = getStart(groupNums, groupNums.length - 1);
        int end = board.length;

        int groupSize = groupNums[groupNums.length - 1];
        for (int bi = start; bi <= end; bi++) {
            // Check whether itself can be placed there
            if (!doesNotContain(board, bi, Math.min(bi + groupSize, end), '.')) {
                continue;
            }
            // Check all spaces after it
            if (doesNotContain(board, bi + groupSize, end, '#')) {
                mem[bi][groupNums.length - 1] = 1;
            }
        }

        // All elements in between
        for (int gi = groupNums.length - 1; gi >= 0; gi--) {
            // Skip some positions that are impossible
            // Determine boundaries of placement
            start = getStart(groupNums, gi);
            end = getEnd(groupNums, gi, board.length); // End is exclusive 0 1 2 3 4 5 6

            groupSize = groupNums[gi];
            for (int bi = start; bi <= end - groupSize; bi++) {
                // Check whether itself can be placed there
                if (!doesNotContain(board, bi, Math.min(bi + groupSize, board.length), '.')) {
                    continue;
                }
                mem[bi][gi] += checkTrailingDotsGetMemoi(mem, board, bi + groupSize, end, gi + 1);
            }
        }

        // Find all possible arrangements for first groupNum
        end = getEnd(groupNums, -1, board.length); // End is exclusive 0 1 2 3 4 5 6

        long total = 0;
        total += mem[0][0]; // Dot goes before the board
        // Check where leading dots can go
        total += checkTrailingDotsGetMemoi(mem, board, 0, end, 0);

        return total;
    }

    public static String unfoldBoard(String s) {
        StringBuilder sBuilder = new StringBuilder(s);
        for (int i = 0; i < 4; i++) {
            sBuilder.append("?").append(s);
        }
        return sBuilder.toString();
    }

    public static String unfoldGroupNums(String s) {
        StringBuilder sBuilder = new StringBuilder(s);
        for (int i = 0; i < 4; i++) {
            sBuilder.append(",").append(s);
        }
        return sBuilder.toString();
    }

    public static long parseInput(List<String> lines) {
        long points = 0;
        for (String line : lines) {
            String[] picross = line.split(" ");
            char[] board = unfoldBoard(picross[0].trim()).toCharArray();
            int[] groupNums = Arrays.stream(unfoldGroupNums(picross[1].trim()).replaceAll("\\.+", ".").split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
            long possibleArrangements = doDynamicProgramming(board, groupNums);
            points += possibleArrangements;
        }
        return points;
    }
}