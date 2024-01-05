import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PartOne {
    private static final String puzzleInput = "2023/Day25/test2.txt";
    private static final int MINCUT = 3;

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static NodeGroup getRandomNodeGroup(HashMap<NodeGroup, ArrayList<NodeGroup>> nodeGroupNeighbours) {
        int index = new Random().nextInt(nodeGroupNeighbours.size());

        Iterator<NodeGroup> ni = nodeGroupNeighbours.keySet().iterator();
        for (int i = 0; i < index - 1; i++) {
            ni.next();
        }

        return ni.next();
    }

    public static int contract(Graph graph) {
        // Every iteration, pick random edge
        // Stop when NodeGroup size == 2
        // If Edges size == 3, return the multiple, else rerun Karger-Stein
        HashMap<NodeGroup, ArrayList<NodeGroup>> nodes = graph.nodes;
        while (nodes.size() > 2) {
            NodeGroup left = getRandomNodeGroup(nodes);
            // Pick random neighbour
            ArrayList<NodeGroup> leftNeighbours = nodes.get(left);
            NodeGroup right = leftNeighbours.get(new Random().nextInt(leftNeighbours.size()));

            // Contract edges
            // Add randomRight to randomLeft node group
            left.addNodeGroup(right);
            ArrayList<NodeGroup> rightNeighbours = nodes.get(right);
            for (NodeGroup rightNeighbour : rightNeighbours) {
                if (rightNeighbour == left) { // For all neighbours of right excluding left
                    continue;
                }
                // Add new neighbour to left
                leftNeighbours.add(rightNeighbour);

                ArrayList<NodeGroup> rnn = nodes.get(rightNeighbour); // RightNeighbour's neighbours
                // Remove right as their neighbour
                rnn.removeAll(Collections.singletonList(right));
                // Add randomLeft as their new neighbour
                rnn.add(left);
            }

            // Remove randomRight as randomLeft's neighbour
            leftNeighbours.removeAll(Collections.singletonList(right));
            // Remove randomRight from node groups
            nodes.remove(right);
        }

        graph.setMinCut();
        if (graph.minCut == MINCUT) {
            int product = 1;
            for (NodeGroup ng : nodes.keySet()) {
                product *= ng.nodes.size();
            }
            return product;
        }

        return -1;
    }

    // Inspiration taken from:
    // https://www.reddit.com/r/adventofcode/comments/18qbsxs/comment/kftp4jr/?utm_source=share&utm_medium=web2x&context=3
    // https://en.wikipedia.org/wiki/Karger's_algorithm
//    public static int kargerStein(HashMap<NodeGroup, ArrayList<NodeGroup>> nodeGroupNeighbours) {
//        if (nodeGroupNeighbours.size() <= 6) {
//            contract(nodeGroupNeighbours, 2);
//            for (NodeGroup ng : nodeGroupNeighbours.keySet()) {
//                if (nodeGroupNeighbours.get(ng).size() == MINCUT) {
//                    int product = 1;
//                    for (NodeGroup ng2 : nodeGroupNeighbours.keySet()) {
//                        product *= ng2.nodes.size();
//                    }
//                    return product;
//                }
//            }
//            return -1;
//        } else {
//            int limit = (int) Math.ceil(1 + (nodeGroupNeighbours.size() / Math.sqrt(2)));
//            HashMap<NodeGroup, ArrayList<NodeGroup>> graphOne = copyGraph(nodeGroupNeighbours);
//            HashMap<NodeGroup, ArrayList<NodeGroup>> graphTwo = copyGraph(nodeGroupNeighbours);
//            contract(graphOne, limit);
//            contract(graphTwo, limit);
//            return Math.min(kargerStein(graphOne), kargerStein(graphTwo));
//        }
//    }

    public static int parseInput(List<String> lines) {
        // Hashset of NodeGroup with edges
        Graph graph = new Graph(lines);

        int product = -1;
        while (product < 0) {
            Graph currentGraph = graph.getCopy();
            product = contract(currentGraph);
        }
        return product;
    }
}
