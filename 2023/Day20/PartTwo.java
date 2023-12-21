import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 26905046628202 not the right answer - didn't count for only one pulse
// 26276913243886 not the right answer - one line of counting was put in the wrong place
//                                       in 1 of the copies of the 4 copies of the same method (I know)
// 5325756669870 not the right answer - forgot about resetting the circuit
// 50938141536 not the right answer - //

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

    public static void reset(Component broadcaster) {
        Queue<Component> q = new LinkedList<>();
        q.add(broadcaster);
        while (!q.isEmpty()) {
            Component c = q.poll();
            c.output = false;
            c.reset = true;
            for (Component o : c.outputComponents) {
                if (!o.reset) {
                    q.add(o);
                }
            }
        }
    }

    public static long findCycle(Component broadcaster, String name) {
        long buttonCount = 0;
        Queue<Tuple> q = new LinkedList<>();
        while (true) {
            buttonCount++;
            q.add(new Tuple(broadcaster, false));
            int countPulse = 0;
            while (!q.isEmpty()) {
                Tuple t = q.poll();
                Component c = t.com;
                if (c.name.equals(name) && !t.input) {
                    countPulse++;
                }
                c.sendPulse(q, t.input);
            }
            if (countPulse == 1) return buttonCount;
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

        Shared.mapComponentMap(lines, componentMap, links, nandInputs);
        // Link all output components and locate rx parent
        Component rxParent = Shared.linkOutputLocateRx(links, componentMap, nandInputs);

        // Find all parents of rx's parent, which should be the ones to look out for
        ArrayList<Component> lcmComponents = rxParent.inputComponents;

        long lcm = 1;
        Component bc = componentMap.get("broadcaster");
        for (Component c : lcmComponents) {
            lcm = smallestCommonMultiple(lcm, findCycle(bc, c.name));
            reset(bc);
        }

        return lcm;
    }
}
