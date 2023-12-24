import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PartOne {
    private static final String puzzleInput = "2023/Day24/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    private static int checkIntersect(Hail h1, Hail h2, long lowerBound, long upperBound) {
        /*
        y = ax + b

        a1x + b1 = a2x + b2
        x = (b2 - b1) / (a1 - a2)
        y = a1 * x + b1
        */

        float a1 = (float) h1.vel[1] / h1.vel[0];
        float a2 = (float) h2.vel[1] / h2.vel[0];
        float b1 = (float) h1.loc[1] - (a1 * h1.loc[0]);
        float b2 = (float) h2.loc[1] - (a2 * h2.loc[0]);

        float x = (b2 - b1) / (a1 - a2);
        float y = (a1 * x) + b1;

        if (x >= lowerBound && x <= upperBound && y >= lowerBound && y <= upperBound) {
            // Check if in bound
            // Check if not crossing in the past
            // Time it takes to get to coordinate = (loc[0] - x) / vel[0]
            if ((x - h1.loc[0]) / h1.vel[0] >= 0 && (x - h2.loc[0]) / h2.vel[0] >= 0){
                return 1;
            }
        }

        return 0;
    }

    public static int parseInput(List<String> lines) {
        ArrayList<Hail> hails = new ArrayList<>();
        final long lowerBound = 200000000000000L;
        final long upperBound = 400000000000000L;
//        final long lowerBound = 7;
//        final long upperBound = 27;

        for (String line : lines) {
            hails.add(new Hail(line));
        }

        int points = 0;
        for (int i = 0; i < hails.size() - 1; i++) {
            for (int j = i + 1; j < hails.size(); j++) {
                points += checkIntersect(hails.get(i), hails.get(j), lowerBound, upperBound);
            }
        }
        return points;
    }
}
