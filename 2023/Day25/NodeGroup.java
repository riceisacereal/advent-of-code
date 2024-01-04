import java.util.HashSet;

public class NodeGroup {
    String id;
    HashSet<String> group;

    public NodeGroup(String parent) {
        group = new HashSet<>();
        id = parent;
        group.add(parent);
    }

    public void addNodeGroup(NodeGroup o) {
        group.addAll(o.group);
    }
}
