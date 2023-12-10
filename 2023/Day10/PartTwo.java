import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 1950 too high
// 392 too high
// 374 too high

public class PartTwo {
    private static final String puzzleInput = "2023/Day10/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static Pipe getStartConnect(int y, int x, Pipe[][] pipeMap) {
        String connect = "";
        // determine shape of start
        // check north
        if (y > 0 && pipeMap[y - 1][x] != null) {
            if (pipeMap[y - 1][x].getConnect().contains("S")) {
                connect += "N";
            }
        }
        // check east
        if (x < pipeMap[0].length - 1 && pipeMap[y][x + 1] != null) {
            if (pipeMap[y][x + 1].getConnect().contains("W")) {
                connect += "E";
            }
        }
        // check south
        if (y < pipeMap.length - 1 && pipeMap[y + 1][x] != null) {
            if (pipeMap[y + 1][x].getConnect().contains("N")) {
                connect += "S";
            }
        }
        // check west
        if (x > 0 && pipeMap[y][x - 1] != null) {
            if (pipeMap[y][x - 1].getConnect().contains("E")) {
                connect += "W";
            }
        }

        Pipe p = new Pipe('S');
        p.setConnect(connect);
        return p;
    }

    public static int parseInput(List<String> lines) {
        Pipe[][] pipeMap = new Pipe[lines.size()][lines.get(0).length()];
        int[] start = null;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            char[] pipes = line.toCharArray();
            for (int j = 0; j < line.length(); j++) {
                char c = pipes[j];
                if (c != '.') {
                    if (c == 'S') {
                        start = new int[] {i, j};
                        continue;
                    }
                    Pipe p = new Pipe(c);
                    pipeMap[i][j] = p;
                }
            }
        }

        HashMap<String, String> opposite = new HashMap<>();
        opposite.put("N", "S");
        opposite.put("S", "N");
        opposite.put("E", "W");
        opposite.put("W", "E");
        // make start pipe and set shape
        Pipe startingPipe = getStartConnect(start[0], start[1], pipeMap);
        pipeMap[start[0]][start[1]] = startingPipe;

        int[] currentPipe = new int[] {start[0], start[1]};
        String currentDirection = opposite.get(startingPipe.getConnect().substring(0, 1));

        ArrayList<char[]> convertedLines = new ArrayList<>();
        for (String line : lines) {
            convertedLines.add(line
                .replaceAll("\\|", "│")
                .replaceAll("-", "─")
                .replaceAll("J", "┘")
                .replaceAll("7", "┐")
                .replaceAll("L", "└")
                .replaceAll("F", "┌").toCharArray());
        }
        HashMap<String, Character> connectToChar = new HashMap<>();
        connectToChar.put("NS", '│');
        connectToChar.put("EW", '─');
        connectToChar.put("NW", '┘');
        connectToChar.put("SW", '┐');
        connectToChar.put("NE", '└');
        connectToChar.put("ES", '┌');
        convertedLines.get(start[0])[start[1]] = connectToChar.get(startingPipe.getConnect());

        do {
            Pipe p = pipeMap[currentPipe[0]][currentPipe[1]];
            p.setPartOfLoop();
//            convertedLines.get(currentPipe[0])[currentPipe[1]] = '▒';
            currentDirection = p.getNextDirection(opposite.get(currentDirection));
            switch (currentDirection) {
                case "N" -> currentPipe[0]--;
                case "E" -> currentPipe[1]++;
                case "S" -> currentPipe[0]++;
                case "W" -> currentPipe[1]--;
                default -> System.out.println("Unrecognized direction: " + currentDirection);
            }
        } while (currentPipe[0] != start[0] || currentPipe[1] != start[1]);

        int points = 0;
        for (int i = 0; i < lines.size(); i++) {
            char[] line = convertedLines.get(i);
            // detect open loop and close loop
            // open loop can be |, L7, L---7, FJ, F----J

            boolean[] partOfLoop = new boolean[line.length];
            for (int j = 0; j < line.length; j++) {
                if (pipeMap[i][j] != null && pipeMap[i][j].isPartOfLoop()) {
                    partOfLoop[j] = true;
                }
            }

            int[] partOfRegex = new int[line.length];
            Pattern pattern = Pattern.compile("│|(┌─*┘)|(└─*┐)"); //(L-*)|(-*J)|(F-*)|(-*7)
            Matcher matcher = pattern.matcher(new String(line));

            // Check all occurrences
            int startLabel = 0;
            while (matcher.find()) {
                startLabel++;
                for (int j = matcher.start(); j < matcher.end(); j++) {
                    partOfRegex[j] = startLabel;
                }
            }

            boolean loopOpen = false;
            int count = 0;
            int currentLabel = 0;
            for (int j = 0; j < line.length; j++) {
                // part of loop but not part of regex - error or just an edge which can be ignored
                // not part of loop but part of regex - random pipes - count
                // not part of loop and not part of regex - ground/random pipes - count

                // toggle open when regex
                if (partOfLoop[j] && (partOfRegex[j] >= 1 && partOfRegex[j] != currentLabel)) {
                    // Part of the loop - get rid of random pipes
                    //
                    // toggle loop
                    loopOpen = !loopOpen;
                    currentLabel = partOfRegex[j];
                } else if (!partOfLoop[j] && loopOpen) {
                    convertedLines.get(i)[j] = 'X';
                    count++;
                }
            }
            points += count;
        }

        for (char[] c : convertedLines) {
            System.out.println(new String(c));
        }

        return points;
    }
}
