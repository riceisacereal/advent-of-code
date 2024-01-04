import java.awt.RadialGradientPaint;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class PartOne {
    private static final String puzzleInput = "2023/Day25/test2.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

//    public static void addIfNotExist(ArrayList<NodeGroup> nodeGroups, NodeGroup ng) {
//        for (NodeGroup nodeGroup : nodeGroups) {
//            if (nodeGroup.id.equals(ng.id)) {
//                return;
//            }
//        }
//        nodeGroups.add(ng);
//    }

    public static void addIfNotExist(HashMap<NodeGroup, ArrayList<NodeGroup>> nodeGroupNeighbours,
                                     NodeGroup ng) {
        if (nodeGroupNeighbours.get(ng) == null) {
            nodeGroupNeighbours.put(ng, new ArrayList<>());
        }
    }

    public static void removeAllEdgesBetween(ArrayList<Edge> edges, NodeGroup left, NodeGroup right) {
        boolean exists = true;
        while (exists) {
            exists = false;
            for (Edge e : edges) {
                if (e.left.id.equals(left.id) && e.right.id.equals(right.id) ||
                    e.left.id.equals(right.id) && e.right.id.equals(left.id)) {
                    exists = true;
                    edges.remove(e);
                    break;
                }
            }
        }
    }

    public static int parseInput(List<String> lines) {
        // Hashset of NodeGroup with edges
//        ArrayList<NodeGroup> nodeGroups = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        HashMap<NodeGroup, ArrayList<NodeGroup>> nodeGroupNeighbours = new HashMap<>();

        for (String line : lines) {
            String[] nodes = line.split(": ");
            NodeGroup left = new NodeGroup(nodes[0].trim());
            addIfNotExist(nodeGroupNeighbours, left);
            for (String r : nodes[1].trim().split(" ")) {
                NodeGroup right = new NodeGroup(r);
                addIfNotExist(nodeGroupNeighbours, right);
                // Add for left
                nodeGroupNeighbours.get(left).add(right);
                // Add for right
                nodeGroupNeighbours.get(right).add(left);
                // Make an edge
                edges.add(new Edge(left, right));
            }
        }

        // Every iteration, pick random edge
        // Stop when NodeGroup size == 2
        // If Edges size == 3, return the multiple, else rerun Karger-Stein
        while (nodeGroupNeighbours.size() > 2) {
            Edge randomEdge = edges.get(new Random().nextInt(edges.size()));
            NodeGroup left = randomEdge.left;
            NodeGroup right = randomEdge.right;
            // Merge nodes into one singular node
            left.addNodeGroup(right);
            for (NodeGroup rightNeighbour : nodeGroupNeighbours.get(randomEdge.right)) {
                // Remove old edge between right and its neighbour
                removeAllEdgesBetween(edges, right, rightNeighbour);
                // Remove from right neighbour map
                nodeGroupNeighbours.get(rightNeighbour).remove(right);
                // Add new edge between left and rightNeighbour
                edges.add(new Edge(left, rightNeighbour));
            }

            // Remove right node
            nodeGroupNeighbours.remove(right);
        }

        int minCut = -1;
        int product = 1;
        for (NodeGroup ng : nodeGroups) {
            minCut = nodeGroupNeighbours.get(ng).size();
            product *= ng.group.size();
        }

        if (minCut != 3) {
            return -1;
        } else {
            return product;
        }
    }
}
