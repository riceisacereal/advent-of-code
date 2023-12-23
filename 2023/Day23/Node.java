import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Node {
    int[] coord;
    HashMap<Node, Integer> connectedNodes;

    public Node(int[] coord) {
        this.coord = coord;
        connectedNodes = new HashMap<>();
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