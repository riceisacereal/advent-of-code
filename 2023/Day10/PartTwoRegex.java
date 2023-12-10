import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartTwoRegex {
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
        int maxY = lines.size();
        int maxX = lines.get(0).length();

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

        Shared.printMap(convertedLines);

        // Replace border pipes with single border, and delete edges
        // Resolve borders by removing all border pairs
        String[] regexMap = new String[maxY];
        for (int i = 0; i < maxY; i++) {
            char[] line = convertedLines[i];
            String s = new String(line);
            s = s.replaceAll("│|(┌─*┘)|(└─*┐)", "│")
                .replaceAll("(┌─*┐)|(└─*┘)", "")
                .replaceAll("││", "");
            regexMap[i] = s;
        }

        Shared.printMap(regexMap);

        // Count enclosed spaces
        int enclosed = 0;
        for (String s : regexMap) {
            Pattern pattern = Pattern.compile("│\\.*│");
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                enclosed += matcher.end() - matcher.start() - 2;
            }
        }

        return enclosed;
    }
}