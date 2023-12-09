import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

// 6692129108387828075880 too high

public class PartTwo {
    private static final String puzzleInput = "2023/Day08/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        long result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
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

    public static long scmFold(ArrayList<Integer> l) {
        long current = l.get(0);
        for (int i = 1; i < l.size(); i++) {
            current = smallestCommonMultiple(current, l.get(i));
        }
        return current;
    }

    public static long parseInput(List<String> lines) {
        String directions = lines.get(0);

        ArrayList<Node> nodes = new ArrayList<>();
        HashMap<String, Node> nodeMap = new HashMap<>();
        for (String line : lines.subList(2, lines.size())) {
            Node cur = new Node(line);
            nodeMap.put(cur.getSelf(), cur);
            nodes.add(cur);
        }

        ArrayList<Node> startingNodes = new ArrayList<>();
        for (Node n : nodes) {
            n.setLeftNode(nodeMap.get(n.getLeft()));
            n.setRightNode(nodeMap.get(n.getRight()));
            if (n.getSelf().charAt(2) == 'A') {
                startingNodes.add(n);
            }
        }

        ArrayList<Integer> loopSizes = new ArrayList<>();
        for (Node n : startingNodes) {
            // Create list of HashSets for optimisation
            ArrayList<HashSet<String>> visited = new ArrayList<>();
            for (int i = 0; i < directions.length(); i++)  {
                visited.add(new HashSet<>());
            }

            Node current = n;
            int count = 0;
            int index = 0;
            while (!visited.get(index).contains(current.getSelf() + index)) {
                visited.get(index).add(current.getSelf() + index);
                switch (directions.charAt(index)) {
                    case 'L' -> current = current.getLeftNode();
                    case 'R' -> current = current.getRightNode();
                }
                count++;
                index = count % directions.length();
            }
            // Assumes that the start of the loop is before the end of the first iteration
            // of the directions - which is the case with the input
            loopSizes.add(count - index);
        }

        // Calculate LCM
        return scmFold(loopSizes);
    }
}
