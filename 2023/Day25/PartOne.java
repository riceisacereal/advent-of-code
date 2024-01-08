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
            // Add randomRight to left node group - merge with node group
            left.addNodeGroup(right);
            ArrayList<NodeGroup> rightNeighbours = nodes.get(right);
            // Remove left as right's neighbour - ignore all edges between them
            rightNeighbours.removeAll(Collections.singletonList(left));
            for (NodeGroup rightNeighbour : rightNeighbours) {
                ArrayList<NodeGroup> rnn = nodes.get(rightNeighbour); // RightNeighbour's neighbours
                // Remove right as their neighbour - disconnect old edges
                rnn.remove(right); // Should be run enough times to remove all instances of right from its neighbours

                // Make new edge
                // Add left as their new neighbour
                rnn.add(left);
                // Add new neighbour to left
                leftNeighbours.add(rightNeighbour);
            }

            // Remove right as left's neighbour
            leftNeighbours.removeAll(Collections.singletonList(right));
            // Remove right from node groups - contracted away
            nodes.remove(right);
        }
    }

    // Inspiration taken from:
    // https://www.reddit.com/r/adventofcode/comments/18qbsxs/comment/kftp4jr/?utm_source=share&utm_medium=web2x&context=3
    // https://en.wikipedia.org/wiki/Karger's_algorithm
    public static Graph kargerStein(Graph graph) {
        HashMap<NodeGroup, ArrayList<NodeGroup>> nodes = graph.nodes;
        if (nodes.size() <= 10) {
            contract(graph, 2);
            graph.setMinCut();
            return graph;
        } else {
            int limit = (int) Math.ceil(1 + (nodes.size() / 3.0));
            Graph graphOne = graph.getCopy();
            Graph graphTwo = graph.getCopy();

            contract(graphOne, limit);
            graphOne = kargerStein(graphOne);
            if (graphOne.minCut == MINCUT) return graphOne;

            contract(graphTwo, limit);
            graphTwo = kargerStein(graphTwo);
            return graphTwo;
        }
    }

    public static int parseInput(List<String> lines) {
        Graph graph = new Graph(lines);

        while (true) {
            Graph currentGraph = graph.getCopy();

            // Karger's only:
            // contract(currentGraph, 2);
            // Karger-Stein:
            currentGraph = kargerStein(currentGraph);

            currentGraph.setMinCut();
            if (currentGraph.minCut == MINCUT) {
                int product = 1;
                for (NodeGroup ng : currentGraph.nodes.keySet()) {
                    product *= ng.nodes.size();
                }
                return product;
            }
        }
    }
}
