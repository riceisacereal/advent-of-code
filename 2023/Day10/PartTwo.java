import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 1950 too high
// 392 too high
// 374 too high

public class PartTwo {
    private static final String puzzleInput = "2023/Day10/input.txt";

    public static void main(String[] args) throws Exception {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int parseInput(List<String> lines) throws Exception {
        int maxX = lines.get(0).length();
        int maxY = lines.size();

        // Build pipe map
        Pipe[][] pipeMap = Shared.getPipeMap(lines);
        int[] startLoc = Shared.getStartingPipe(lines);
        Pipe startingPipe = pipeMap[startLoc[0]][startLoc[1]];
        Shared.setPipeLoop(startLoc, startingPipe, pipeMap);

        // Convert to all ground + better map representation with box characters
        char[][] convertedLines = new char[maxY][maxX];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                Pipe p = pipeMap[i][j];
                if (p != null && p.isPartOfLoop()) {
                    convertedLines[i][j] = Shared.betterMapRep.get(p.getPipe());
                } else {
                    convertedLines[i][j] = '.';
                }
            }
        }

        // Scan per line from left to right the enclosed spaces
        int points = 0;
        for (int i = 0; i < maxY; i++) {
            String line = new String(convertedLines[i]);

            // Match edges of pipe loop with regex
            // Detect open loop and close loop
            int[] partOfRegex = new int[line.length()];
            Pattern pattern = Pattern.compile("│|(┌─*┘)|(└─*┐)");
            Matcher matcher = pattern.matcher(line);
            // Label all starting and ending pipes
            int startLabel = 0;
            // https://stackoverflow.com/a/8938549
            while (matcher.find()) {
                startLabel++;
                for (int j = matcher.start(); j < matcher.end(); j++) {
                    partOfRegex[j] = startLabel;
                }
            }

            int count = 0;
            int currentLabel = 0;
            boolean loopOpen = false;
            for (int j = 0; j < maxX; j++) {
                if (partOfRegex[j] > currentLabel) {
                    // Toggle loop when new label encountered
                    loopOpen = !loopOpen;
                    currentLabel++;
                } else if (convertedLines[i][j] == '.' && loopOpen) {
                    // If loop open can still encounter edges ┌─┐ └─┘ which shouldn't be counted
                    convertedLines[i][j] = 'X';
                    count++;
                }
            }
            points += count;
        }
        Shared.printMap(convertedLines);

        return points;
    }
}
