import java.util.HashSet;

public class NodeGroup {
    String id;
    HashSet<String> nodes;

    public NodeGroup(String parent) {
        nodes = new HashSet<>();
        id = parent;
        nodes.add(parent);
    }

    public void addNodeGroup(NodeGroup o) {
        nodes.addAll(o.nodes);
    }
}
