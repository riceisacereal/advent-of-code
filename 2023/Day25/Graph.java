import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Graph {
    HashMap<NodeGroup, ArrayList<NodeGroup>> nodes;
    int minCut;

    public Graph(List<String> lines) {
        nodes = new HashMap<>();

        for (String line : lines) {
            String[] temp = line.split(": ");

            NodeGroup left = getIfExists(temp[0].trim());
            for (String r : temp[1].trim().split(" ")) {
                NodeGroup right = getIfExists(r);
                // Add for left
                nodes.get(left).add(right);
                // Add for right
                nodes.get(right).add(left);
            }
        }
    }

    private NodeGroup getIfExists(String s) {
        for (NodeGroup ng : nodes.keySet()) {
            if (ng.id.equals(s)) {
                return ng;
            }
        }
        // Does not exist
        NodeGroup newGroup = new NodeGroup(s);
        nodes.put(newGroup, new ArrayList<>());
        return newGroup;
    }

    public Graph getCopy() {
        Graph newGraph = new Graph(new ArrayList<>());
        newGraph.nodes = new HashMap<>();

//        HashMap<NodeGroup, ArrayList<NodeGroup>> newNodes = new HashMap<>();
        for (Map.Entry<NodeGroup, ArrayList<NodeGroup>> e : this.nodes.entrySet()) {
            // Make new node group
            NodeGroup group = e.getKey();
            NodeGroup newGroup = newGraph.getIfExists(group.id);
            newGroup.nodes = new HashSet<>(group.nodes);

            // Make new arraylist of neighbours
            ArrayList<NodeGroup> neighbours = new ArrayList<>();
            for (NodeGroup ng : e.getValue()) {
                neighbours.add(newGraph.getIfExists(ng.id));
            }
            newGraph.nodes.put(newGroup, neighbours);
        }

        return newGraph;
    }

    public void setMinCut() {
        if (nodes.size() != 2) throw new IllegalArgumentException("Not completely contracted");

        for (ArrayList<NodeGroup> neighbours : nodes.values()) {
            minCut = neighbours.size();
            break;
        }
    }
}
