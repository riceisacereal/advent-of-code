import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class Pipe {
//    public static HashMap<Character, String> shapeMapConnect = new HashMap() {
//        '|':
//    };
    private char pipe;
    private String connect;

    /*   N
    * W     E
    *    S
    * */

    public Pipe(char p) {
        this.pipe = p;
        // TODO: make into hashmap
        switch (p) {
            case '|' -> this.connect = "NS";
            case '-' -> this.connect = "EW";
            case 'L' -> this.connect = "NE";
            case 'J' -> this.connect = "NW";
            case '7' -> this.connect = "SW";
            case 'F' -> this.connect = "ES";
            case 'S' -> this.connect = "";
            default -> System.out.println("Unexpected symbol: " + p);
        }
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public String getNextDirection(String direction) {
        return this.connect.replaceAll(direction, "");
    }

    @Override
    public String toString() {
        return "" + pipe;
    }
}

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
//        currentDirection = startingPipe.getNextDirection(currentDirection);
//        points++;

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

//        for (int i = Math.max(0, start[0] - 1); i < Math.min(lines.size(), start[0] + 2); i++) {
//            for (int j = Math.max(0, start[1] - 1); j < Math.min(lines.get(0).length(), start[1] + 2); j++) {
//
//            }
//        }


        // pick a way to go
        // count steps
        // check if it's the start

//        System.out.println(Arrays.deepToString(pipeMap));

        return points;
    }
}
