import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

// 6692129108387828075880 too high
// 6692129108387828075880

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

    public static Boolean isAllEnd(ArrayList<Node> currentNodes) {
        for (Node n : currentNodes) {
            if (!n.isEnd()) {
                return false;
            }
        }
        return true;
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

        ArrayList<Node> currentNodes = new ArrayList<>();
        for (Node n : nodes) {
            n.setLeftNode(nodeMap.get(n.getLeft()));
            n.setRightNode(nodeMap.get(n.getRight()));
            if (n.getSelf().charAt(2) == 'A') {
                currentNodes.add(n);
            }
        }

        ArrayList<Integer> loopSizes = new ArrayList<>();
        for (Node n : currentNodes) {
            ArrayList<String> visited = new ArrayList<>();
            Node current = n;
            int index = 0;
            while (!visited.contains(current.getSelf() + index)) {
                visited.add(current.getSelf() + index);
                switch (directions.charAt(index)) {
                    case 'L' -> current = current.getLeftNode();
                    case 'R' -> current = current.getRightNode();
                }
                index = (index + 1) % directions.length();
            }
            loopSizes.add(visited.size() - visited.indexOf(current.getSelf() + index));
        }

        // calculate LCM
        System.out.println(loopSizes);
        return scmFold(loopSizes);
    }
}
