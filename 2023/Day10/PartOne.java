import static java.util.Map.entry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class PartOne {
    private static final String puzzleInput = "2023/Day10/input.txt";
    private static final int[][] directionDisplacement = new int[][] {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    // The following two variables are only needed for determining the starting symbol
    private static final String[] connectingShapes = new String[] {"|7F", "-J7", "|JL", "-LF"};
    private static final Map<String, Character> connectionToSymbol = Map.ofEntries(
        entry("02", '|'),
        entry("13", '-'),
        entry("01", 'L'),
        entry("12", 'F'),
        entry("23", '7'),
        entry("03", 'J')
    );

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static char getStartChar(int y, int x, List<String> lines) {
        StringBuilder connection = new StringBuilder();
        int maxX = lines.get(0).length();
        int maxY = lines.size();
        for (int i = 0; i < 4; i++) {
            int newX = x + directionDisplacement[i][1];
            int newY = y + directionDisplacement[i][0];
            if (newX >= 0 && newX < maxX && newY >= 0 && newY < maxY) {
                char currentChar = lines.get(newY).charAt(newX);
                if (connectingShapes[i].contains(currentChar + "")) {
                    connection.append(i);
                }
            }
        }
        return connectionToSymbol.get(connection.toString());
    }

    public static int parseInput(List<String> lines) {
        int x = lines.get(0).length();
        int y = lines.size();

        Pipe[][] pipeMap = new Pipe[y][x];
        int[] startLoc = null;
        for (int i = 0; i < y; i++) {
            String line = lines.get(i);
            for (int j = 0; j < x; j++) {
                char c = line.charAt(j);
                if (c != '.') {
                    if (c == 'S') {
                        // Found starting location
                        startLoc = new int[] {i, j};
                        continue;
                    }
                    Pipe p = new Pipe(c);
                    pipeMap[i][j] = p;
                }
            }
        }
        // Make starting pipe
        Pipe startingPipe = new Pipe(getStartChar(startLoc[0], startLoc[1], lines));
        pipeMap[startLoc[0]][startLoc[1]] = startingPipe;

        // Prepare iterative loop traversing
        int pipes = 0;
        int[] currentPipe = new int[] {startLoc[0], startLoc[1]};
        int currentDirection = (startingPipe.getRandomStart() + 2) % 4; // Use opposite for the loop
        do {
            Pipe p = pipeMap[currentPipe[0]][currentPipe[1]];
            currentDirection = p.getNextDirection((currentDirection + 2) % 4);
            currentPipe[1] = currentPipe[1] + directionDisplacement[currentDirection][1];
            currentPipe[0] = currentPipe[0] + directionDisplacement[currentDirection][0];
            pipes++;
        } while (currentPipe[0] != startLoc[0] || currentPipe[1] != startLoc[1]);

        return pipes / 2;
    }
}
