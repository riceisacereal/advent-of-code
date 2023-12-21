import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 628332964 too low - forgot about the pulse handling order and gave every component an input attribute

public class PartOne {
    private static final String puzzleInput = "2023/Day20/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int parseInput(List<String> lines) {
        HashMap<String, String[]> links = new HashMap<>();
        HashMap<String, Component> componentMap = new HashMap<>();
        ArrayList<String> nandInputs = new ArrayList<>();

        Shared.mapComponentMap(lines, componentMap, links, nandInputs);
        Shared.linkOutputLocateRx(links, componentMap, nandInputs); // Ignore return value meant for part 2

        int highCount = 0;
        int lowCount = 0;
        Component broadcaster = componentMap.get("broadcaster");
        Queue<Tuple> q = new LinkedList<>();
        for (int i = 0; i < 1000; i++) {
            lowCount++; // For the button
            q.add(new Tuple(broadcaster, false));
            while (!q.isEmpty()) {
                Tuple t = q.poll();
                Component c = t.c;
                // Amount of high/low pulses sent
                long pulses = c.sendPulse(q, t.input);
                if (pulses == 0) {
                    continue;
                }

                if (pulses >= 0) {
                    highCount += pulses;
                } else {
                    lowCount -= pulses;
                }
            }
        }

        return highCount * lowCount;
    }
}
