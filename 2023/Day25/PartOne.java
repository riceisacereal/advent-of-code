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

    public static void addIfNotExist(ArrayList<NodeGroup> nodeGroups, NodeGroup ng) {
        for (NodeGroup nodeGroup : nodeGroups) {
            if (nodeGroup.id.equals(ng.id)) {
                return;
            }
        }
        nodeGroups.add(ng);
    }

    public static int parseInput(List<String> lines) {
        // Hashset of NodeGroup with edges
        ArrayList<NodeGroup> nodeGroups = new ArrayList<>();
        HashMap<NodeGroup, ArrayList<NodeGroup>> nodeGroupNeighbours = new HashMap<>();

        for (String line : lines) {
            String[] nodes = line.split(": ");
            NodeGroup left = new NodeGroup(nodes[0].trim());
            addIfNotExist(nodeGroups, left);
            if (nodeGroupNeighbours.get(left) == null) {
                nodeGroupNeighbours.put(left, new ArrayList<>());
            }
            for (String r : nodes[1].trim().split(" ")) {
                NodeGroup right = new NodeGroup(r);
                addIfNotExist(nodeGroups, right);
                // Add for left
                nodeGroupNeighbours.get(left).add(right);
                // Add for right
                if (nodeGroupNeighbours.get(right) == null) {
                    nodeGroupNeighbours.put(right, new ArrayList<>());
                }
                nodeGroupNeighbours.get(right).add(left);
            }
        }

        // Every iteration, pick random edge
        // Stop when NodeGroup size == 2
        // If Edges size == 3, return the multiple, else rerun Karger-Stein
        while (nodeGroups.size() > 2) {
            NodeGroup randomLeft = nodeGroups.get(new Random().nextInt(nodeGroups.size()));
            // Pick random neighbour
            ArrayList<NodeGroup> leftNeighbours = nodeGroupNeighbours.get(randomLeft);
            if (leftNeighbours.size() == 0) {
                System.out.println("Uh oh");
            }
            NodeGroup randomRight = leftNeighbours.get(new Random().nextInt(leftNeighbours.size()));

            // Contract edges
            ArrayList<NodeGroup> rightNeighbours = nodeGroupNeighbours.get(randomRight);
            for (NodeGroup ng : rightNeighbours) {
                if (ng == randomLeft) {
                    continue;
                }
                // For each repeated edge repeat neighbour (does that work?)
                leftNeighbours.add(ng);
                // Remove randomRight as their neighbour
                nodeGroupNeighbours.get(ng).removeAll(Collections.singletonList(randomRight));
                // Add randomLeft as their new neighbour
                nodeGroupNeighbours.get(ng).add(randomLeft);
                // If node has no more neighbours, remove node
            }

            // Remove randomRight as randomLeft's neighbour
            nodeGroupNeighbours.get(randomLeft).removeAll(Collections.singletonList(randomRight));
            // Add randomRight to randomLeft node group
            randomLeft.addNodeGroup(randomRight);
            // Remove randomRight from node groups
            nodeGroups.removeAll(Collections.singletonList(randomRight));
            nodeGroupNeighbours.remove(randomRight);
            System.out.println(nodeGroups.size());
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
