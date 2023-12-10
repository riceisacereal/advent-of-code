import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class PartOne {
    private static final String puzzleInput = "2023/Day10/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result / 2);
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
        int points = 0;

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

        do {
            Pipe p = pipeMap[currentPipe[0]][currentPipe[1]];
            currentDirection = p.getNextDirection(opposite.get(currentDirection));
            switch (currentDirection) {
                case "N" -> currentPipe[0]--;
                case "E" -> currentPipe[1]++;
                case "S" -> currentPipe[0]++;
                case "W" -> currentPipe[1]--;
                default -> System.out.println("Unrecognized direction: " + currentDirection);
            }
            points++;
        } while (currentPipe[0] != start[0] || currentPipe[1] != start[1]);

        return points;
    }
}
