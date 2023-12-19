import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartTwo {
    private static final String puzzleInput = "2023/Day18/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        double result = parseInput(lines);
        System.out.print(new BigDecimal(result).toPlainString());
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    // https://en.wikipedia.org/wiki/Shoelace_formula
    public static double calculateArea(int[][] points) {
        double area = 0;
        for (int i = 0; i < points.length; i++) {
            int j = (i + 1) % points.length;
            area += (double) (points[i][0] + points[j][0]) * (points[i][1] - points[j][1]);
        }
        return Math.abs(area / 2.0);
    }

    public static double parseInput(List<String> lines) {
        int[][] points = new int[lines.size()][2];
        points[0][0] = 0;
        points[0][1] = 0;

        long countEdge = 0;
        for (int i = 0; i < lines.size() - 1; i++) {
            String line = lines.get(i);
            String[] comp = line.split(" ");

            String hex = comp[2].substring(2, comp[2].length() - 2);
            int d = Integer.parseInt(hex, 16);
            countEdge += d;

            points[i + 1][0] = points[i][0];
            points[i + 1][1] = points[i][1];
            switch (comp[2].charAt(comp[2].length() - 2)) {
                case '3' -> points[i + 1][0] -= d;
                case '1' -> points[i + 1][0] += d;
                case '2' -> points[i + 1][1] -= d;
                case '0' -> points[i + 1][1] += d;
            }
        }
        String[] comp = lines.get(lines.size() - 1).split(" ");
        int d = Integer.parseInt(comp[2].substring(2, comp[2].length() - 2), 16);
        countEdge += d;

        return calculateArea(points) + (countEdge / 2.0) + 1;
    }
}
