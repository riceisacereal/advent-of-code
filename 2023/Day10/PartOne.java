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
        Pipe p = startingPipe;
        int pipes = 0;
        int[] nextPipe = new int[] {startLoc[0], startLoc[1]};
        int currentDirection = (startingPipe.getRandomStart() + 2) % 4; // Use opposite for the loop
        do {
            currentDirection = p.getNextDirection((currentDirection + 2) % 4);
            nextPipe[1] = nextPipe[1] + Shared.directionDisplacement[currentDirection][1];
            nextPipe[0] = nextPipe[0] + Shared.directionDisplacement[currentDirection][0];
            p = pipeMap[nextPipe[0]][nextPipe[1]];
            pipes++;
        } while (p != startingPipe);

        return pipes / 2;
    }
}
