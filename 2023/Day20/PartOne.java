import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// 628332964 too low

public class PartOne {
    private static final String puzzleInput = "2023/Day20/test2.txt";

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

        // Create all components and put links into map
        for (String line : lines) {
            String[] link = line.split(" -> ");
            Component c = new Component(link[0]);
            componentMap.put(c.name, c);
            links.put(c.name, link[1].split(", "));
            if (c.type == '&') nandInputs.add(c.name);
        }

        // Link all output components
        for (Map.Entry<String, String[]> e : links.entrySet()) {
            Component parent = componentMap.get(e.getKey());
            for (String child : e.getValue()) {
                Component c = componentMap.get(child);
                if (c != null) parent.addOutput(c);
                // Link input components for NAND
                if (nandInputs.contains(child)) {
                    Component nand = componentMap.get(child);
                    nand.inputComponents.add(parent);
                }
            }
        }

        int highCount = 0;
        int lowCount = 0;
        Component broadcaster = componentMap.get("broadcaster");
        Queue<Tuple> q = new LinkedList<>();
        for (int i = 0; i < 1; i++) {
            lowCount++; // For the button
            q.add(new Tuple(broadcaster, false));
            while (!q.isEmpty()) {
                Tuple t = q.poll();
                Component c = t.c;
                // amount of high/low pulses sent
                // add sent components to queue
                long pulses = c.sendPulse(q, t.input);
                System.out.println(c.name + " " + pulses);
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

        System.out.println(lowCount + " " + highCount);
        return highCount * lowCount;
    }
}
