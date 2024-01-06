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
    private static final String puzzleInput = "2023/Day25/input.txt";
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

    public static void contract(Graph graph, int limit) {
        // Every iteration, pick random edge
        // Stop when NodeGroup size == 2
        // If Edges size == 3, return the multiple, else rerun
        HashMap<NodeGroup, ArrayList<NodeGroup>> nodes = graph.nodes;
        while (nodes.size() > limit) {
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
    }

    // Inspiration taken from:
    // https://www.reddit.com/r/adventofcode/comments/18qbsxs/comment/kftp4jr/?utm_source=share&utm_medium=web2x&context=3
    // https://en.wikipedia.org/wiki/Karger's_algorithm
    public static Graph kargerStein(Graph graph) {
        HashMap<NodeGroup, ArrayList<NodeGroup>> nodes = graph.nodes;
        if (nodes.size() <= 50) {
            contract(graph, 2);
            // Set the minimum cut
            graph.setMinCut();
////            System.out.println(graph.minCut);
//            if (graph.minCut == MINCUT) {
//                int product = 1;
//                for (NodeGroup ng : graph.nodes.keySet()) {
//                    product *= ng.nodes.size();
//                }
//                System.out.println(product);
//                System.exit(0);
//            }
            return graph;
        } else {
            int limit = (int) Math.ceil(1 + (nodes.size() / Math.sqrt(2)));
            Graph graphOne = graph.getCopy();
            Graph graphTwo = graph.getCopy();
            contract(graphOne, limit);
            contract(graphTwo, limit);

            Graph minCutOne = kargerStein(graphOne);
            Graph minCutTwo = kargerStein(graphTwo);
            if (minCutOne.minCut < minCutTwo.minCut) {
                return minCutOne;
            } else {
                return minCutTwo;
            }
        }
    }

    public static int parseInput(List<String> lines) {
        // Hashset of NodeGroup with edges
        Graph graph = new Graph(lines);

        long time1 = System.currentTimeMillis();
        while (true) {
            Graph currentGraph = graph.getCopy();
            contract(currentGraph, 2);
            currentGraph.setMinCut();
            if (currentGraph.minCut == 3) {
                int product = 1;
                for (NodeGroup ng : currentGraph.nodes.keySet()) {
                    product *= ng.nodes.size();
                }
                System.out.println(product);
                break;
            }
        }
        long time2 = System.currentTimeMillis();
        while (true) {
            Graph minCutGraph = kargerStein(graph.getCopy());
            if (minCutGraph.minCut == MINCUT) {
                int product = 1;
                for (NodeGroup ng : minCutGraph.nodes.keySet()) {
                    product *= ng.nodes.size();
                }
                System.out.println(product);
                break;
            }
        }
        long time3 = System.currentTimeMillis();
        System.out.print("Time ms: ");
        System.out.println(time2 - time1);
        System.out.print("Time ms: ");
        System.out.println(time3 - time2);
        return 0;
    }
}
