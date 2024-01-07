import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class Node {
    int[] coord;
    HashMap<Node, Integer> connectedNodes;

    public Node(int[] coord) {
        this.coord = coord;
        connectedNodes = new HashMap<>();
    }

    public Node getFirstNeighbour() {
        Iterator<Node> i = connectedNodes.keySet().iterator();
        return i.next();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;
        return Arrays.equals(coord, node.coord);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coord);
    }
}