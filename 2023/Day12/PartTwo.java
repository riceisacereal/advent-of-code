import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class PartTwo {
    private static final String puzzleInput = "2023/Day12/test.txt";

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

    public static long doDynamicProgramming(char[] board, int[] groupNums) {
        // make two-dimensional array of board * groupNums
        long[][] mem = new long[board.length + 2][groupNums.length + 1];
        // Fill last 1 col with 0 [board.length + 1][all]
//        for (int i = 0; i < groupNums.length + 1; i++) {
//            mem[board.length + 1][i] = 1;
//        }
        // Fill last 1 col with 0 [all][groupNums.length]
//        for (int i = 0; i < board.length + 2; i++) {
//            mem[i][groupNums.length] = 1;
//        }

        // work from back to front
        // determine for each groupNum starting at each board position
        // 1. whether itself can be placed there
        // 2. sum up the possible arrangements after it by: spacing out one dot, then two, then tree...
        //    (the added value would be mem[bi + size + dots][gi + 1])
        //    stop when a '#' is hit
        //    put the sum of all these values in mem[bi][gi]

        // Last element is special case where if it fits itself, mem[bi][gi] = 1;

        for (int gi = groupNums.length - 1; gi >= 0; gi--) {
            // skip some positions that are impossible
            // determine boundaries of placement
            int start = 0;
            for (int i = 0; i < gi; i++) {
                start += groupNums[i] + 1;
            }

            int end = board.length; // End is exclusive 0 1 2 3 4 5 6
            for (int i = groupNums.length - 1; i > gi; i--) {
                end -= groupNums[i] + 1;
            }

            int groupSize = groupNums[gi];
            for (int bi = start; bi <= end - groupSize; bi++) {
                // check whether itself can be placed there
                if (!doesNotContain(board, bi, Math.min(bi + groupSize, board.length), '.')) {
                    continue;
                }

                // check possible arrangements after this
                // check if all can be dots
                for (int di = bi + groupSize; di <= end; di++) {
                    if (doesNotContain(board, bi + groupSize, Math.min(di + 1, board.length), '#')) {
                        if (gi == groupNums.length - 1) {
                            mem[bi][gi] = 1;
                        } else {
                            mem[bi][gi] += mem[di + 1][gi + 1];
                        }
                    } else {
                        // Break when '#' is hit (I think)
                        break;
                    }
                }
            }
        }

        // find all possible arrangements for first groupNum
        int end = board.length; // End is exclusive 0 1 2 3 4 5 6
        for (int i = groupNums.length - 1; i >= 0; i--) {
            end -= groupNums[i] + 1;
        }

        long total = 0;
        total += mem[0][0]; // always possible if element is possible
        // Check where leading dots can go
        for (int di = 0; di <= end; di++) {
            if (doesNotContain(board, 0, Math.min(di + 1, board.length), '#')) {
                total += mem[di + 1][0];
            } else {
                // Break when '#' is hit (I think)
                break;
            }
        }

        return total;
        // once entire table is filled, sum up everything in row/col of first groupNum
        // do dynamic programming let's go
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
        int lineNum = 1;
        long points = 0;
        for (String line : lines) {
            String[] picross = line.split(" ");
            char[] board = unfoldBoard(picross[0].trim()).toCharArray();
            int[] groupNums = Arrays.stream(unfoldGroupNums(picross[1].trim().replaceAll("\\.+", ".")).split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
            long possibleArrangements = doDynamicProgramming(board, groupNums);
            System.out.println(lineNum + ": " + possibleArrangements);
            points += possibleArrangements;
            lineNum++;
        }
        return points;
    }
}
