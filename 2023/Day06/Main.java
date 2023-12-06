import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final String puzzleInput = "2023/Day06/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        System.out.println(partOne(lines));
        System.out.println(partTwo(lines));
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int partTwo(List<String> lines) {
        int time = Integer.parseInt(lines.get(0).split(": ")[1].trim().replaceAll(" ", ""));
        long record = Long.parseLong(lines.get(1).split(": ")[1].trim().replaceAll(" ", ""));
        int count = 0;

        for (int b = 1; b < time; b++) {
            long dist = (long) b * (time - b);
            if (dist > record) {
                count++;
            }
        }

        return count;
    }

    public static int partOne(List<String> lines) {
        int points = 1;

        int[] times = Arrays.stream(lines.get(0).split(": ")[1].trim().split(" +"))
            .mapToInt(Integer::parseInt)
            .toArray();
        int[] record = Arrays.stream(lines.get(1).split(": ")[1].trim().split(" +"))
            .mapToInt(Integer::parseInt)
            .toArray();

        for (int i = 0;i < times.length; i++) {
            int count = 0;
            int curTime = times[i];
            for (int b = 1; b < curTime; b++) {
                int dist = b * (curTime - b);
                if (dist > record[i]) {
                    count++;
                }
            }
            points *= count;
        }

        return points;
    }
}
