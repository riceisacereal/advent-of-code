import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 52690 too high

public class PartOne {
    private static final String puzzleInput = "2023/Day18/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int parseInput(List<String> lines) {
        int up = 0;
        int down = 0;
        int left = 0;
        int right = 0;
        int v = 0;
        int h = 0;

        int countPipes = 0;
        Dig[] digs = new Dig[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] comp = line.split(" ");

            int d = Integer.parseInt(comp[1]);
            digs[i] = new Dig(comp[0], d);
            countPipes += d;
            switch (comp[0]) {
                case "U" -> {
                    v -= d;
                    up = Math.min(up, v);
                }
                case "D" -> {
                    v += d;
                    down = Math.max(down, v);
                }
                case "L" -> {
                    h -= d;
                    left = Math.min(left, h);
                }
                case "R" -> {
                    h += d;
                    right = Math.max(right, h);
                }
            }
        }

        int maxY = down - up + 1;
        int maxX = right - left + 1;
        char[][] terrain = new char[maxY][maxX];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                terrain[i][j]= '.';
            }
        }
        int[] startPos = new int[] {-up, -left};

        Map<String, Character> connectToPipe = Map.of(
            "UR", '┌', "RD", '┐', "DL", '┘', "LU", '└',
            "RU", '┘', "DR", '└', "LD", '┌', "UL", '┐');
        for (int di = 0; di < lines.size(); di++) {
            Dig dig = digs[di];
            int d = dig.blocks;
            switch (dig.direction) { // │|(┌─*┘)|(└─*┐)", "│"
                case "U" -> {
                    startPos[0]--;
                    for (int i = 0; i < d - 1; i++) {
                        terrain[startPos[0]][startPos[1]] = '│';
                        startPos[0]--;
                    }
                }
                case "D" -> {
                    startPos[0]++;
                    for (int i = 0; i < d - 1; i++) {
                        terrain[startPos[0]][startPos[1]] = '│';
                        startPos[0]++;
                    }
                }
                case "L" -> {
                    startPos[1]--;
                    for (int i = 0; i < d - 1; i++) {
                        terrain[startPos[0]][startPos[1]] = '─';
                        startPos[1]--;
                    }
                }
                case "R" -> {
                    startPos[1]++;
                    for (int i = 0; i < d - 1; i++) {
                        terrain[startPos[0]][startPos[1]] = '─';
                        startPos[1]++;
                    }
                }
            }
            terrain[startPos[0]][startPos[1]] = connectToPipe.get(dig.direction + digs[(di + 1) % digs.length].direction);
        }

        String[] regexMap = new String[maxY];
        for (int i = 0; i < maxY; i++) {
            char[] line = terrain[i];
            String s = new String(line);
            s = s.replaceAll("│|(┌─*┘)|(└─*┐)", "│")
                .replaceAll("(┌─*┐)|(└─*┘)", "")
                .replaceAll("││", "");
            regexMap[i] = s;
        }

        int enclosed = 0;
        for (String s : regexMap) {
            Pattern pattern = Pattern.compile("│\\.*│");
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                enclosed += matcher.end() - matcher.start() - 2;
            }
        }

        return countPipes + enclosed;
    }
}
