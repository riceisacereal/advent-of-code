import static java.util.Map.entry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class PartOne {
    private static final String puzzleInput = "2023/Day10/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int parseInput(List<String> lines) {
        int maxX = lines.get(0).length();
        int maxY = lines.size();

        Pipe[][] pipeMap = new Pipe[maxY][maxX];
        int[] startLoc = null;
        for (int i = 0; i < maxY; i++) {
            String line = lines.get(i);
            for (int j = 0; j < maxX; j++) {
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
        Pipe startingPipe = new Pipe(Shared.getStartChar(startLoc[0], startLoc[1], lines));
        pipeMap[startLoc[0]][startLoc[1]] = startingPipe;

        // Prepare iterative loop traversing
        int pipes = 0;
        int[] currentPipe = new int[] {startLoc[0], startLoc[1]};
        int currentDirection = (startingPipe.getRandomStart() + 2) % 4; // Use opposite for the loop
        do {
            Pipe p = pipeMap[currentPipe[0]][currentPipe[1]];
            currentDirection = p.getNextDirection((currentDirection + 2) % 4);
            currentPipe[1] = currentPipe[1] + Shared.directionDisplacement[currentDirection][1];
            currentPipe[0] = currentPipe[0] + Shared.directionDisplacement[currentDirection][0];
            pipes++;
        } while (currentPipe[0] != startLoc[0] || currentPipe[1] != startLoc[1]);

        return pipes / 2;
    }
}
