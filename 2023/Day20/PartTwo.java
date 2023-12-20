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

// 26905046628202 not the right answer
// 26276913243886 not the right answer
// 5325756669870 not the right answer
// 50938141536 not the right answer

public class PartTwo {
    private static final String puzzleInput = "2023/Day20/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        long result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static long findCycle(Component broadcaster, String name) {
        long buttonCount = 0;
        Queue<Tuple> q = new LinkedList<>();
        while (true) {
            int count = 0;
            buttonCount++;
            q.add(new Tuple(broadcaster, false));
            while (!q.isEmpty()) {
                Tuple t = q.poll();
                Component c = t.c;
                if (c.name.equals(name) && !t.input) {
                    count++;
                }
                c.sendPulse(q, t.input);
            }
            if (count == 1) return buttonCount;
        }
    }

    public static long smallestCommonMultiple(long a, long b) {
        return (a * b) / greatestCommonFactor(a, b);
    }

    // Euclidean algorithm: https://stackoverflow.com/a/45218296
    public static long greatestCommonFactor(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return (greatestCommonFactor(b, a % b));
        }
    }

    public static long parseInput(List<String> lines) {
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
                if (c != null) {
                    parent.addOutput(c);
                } else {
                    parent.addOutput(new Component(child)); // Add dummy component
                }
                // Link input components for NAND
                if (nandInputs.contains(child)) {
                    Component nand = componentMap.get(child);
                    nand.inputComponents.add(parent);
                }
            }
        }

        ArrayList<Long> nums = new ArrayList<>();
        Component bc = componentMap.get("broadcaster");
        String[] names = new String[] {"qh", "pv", "xm", "hz"};
        for (String name : names) {
            nums.add(findCycle(bc, name));
        }
        long lcm = 1;
        for (int i = 0; i < 4; i++) {
            System.out.println(nums.get(i));
            lcm = smallestCommonMultiple(lcm, nums.get(i));
        }

        return lcm;
    }
}
