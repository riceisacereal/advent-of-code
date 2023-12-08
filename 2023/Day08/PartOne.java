import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PartOne {
    private static final String puzzleInput = "2023/Day08/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int parseInput(List<String> lines) {
        int count = 0;
        String directions = lines.get(0);

        ArrayList<Node> nodes = new ArrayList<>();
        HashMap<String, Node> nodeMap = new HashMap<>();
        for (String line : lines.subList(2, lines.size())) {
            Node cur = new Node(line);
            nodeMap.put(cur.getSelf(), cur);
            nodes.add(cur);
        }

        for (Node n : nodes) {
            n.setLeftNode(nodeMap.get(n.getLeft()));
            n.setRightNode(nodeMap.get(n.getRight()));
        }
        Node start = nodeMap.get("AAA");
        Node end = nodeMap.get("ZZZ");

        Node n = start;
        while (true) {
            for (int i = 0; i < directions.length(); i++) {
                if (n == end) {
                    return count;
                }
                switch (directions.charAt(i)) {
                    case 'L' -> n = n.getLeftNode();
                    case 'R' -> n = n.getRightNode();
                }
                count++;
            }
        }
    }
}
