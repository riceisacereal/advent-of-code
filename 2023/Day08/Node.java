public class Node {
    private final String self;
    private final String left;
    private final String right;
    private Node leftNode;
    private Node rightNode;

    public Node(String line) {
        // AAA = (BBB, BBB)
        String[] a = line.split(" = ");
        this.self = a[0];
        String[] b = a[1].replaceAll("\\(|\\)", "").split(", ");
        this.left = b[0];
        this.right = b[1];
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public void setLeftNode(Node n) {
        this.leftNode = n;
    }

    public void setRightNode(Node n) {
        this.rightNode = n;
    }

    public String getSelf() {
        return self;
    }

    @Override
    public String toString() {
        return self + " = " + "(" + left + ", " + right + ")";
    }
}
