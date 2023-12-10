import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PartOne {
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
        Pipe[][] pipeMap = Shared.getPipeMap(lines);
        // Get starting pipe
        int[] startLoc = Shared.getStartingPipe(lines);
        Pipe startingPipe = pipeMap[startLoc[0]][startLoc[1]];

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
