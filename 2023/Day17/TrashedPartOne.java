import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class TrashedPartOne {
    static class Node {
        // heatloss, shortestdistance + prevnode,
        // direction that have been visited + blockstraveled, heuristicvalue = heatloss + manhatten distance to the end
        // coordinates
        int heatLoss;
        int[] location;
        boolean[][] visited;
        // A*
        int heuristicValue;
        // Keeping track of max 3 consecutive
        int direction;
        int blocksTravelled;
        // Keeping track of shortest path
        int shortestDistance;
        Node prevNode;

        public Node(int heatLoss, int y, int x) {
            this.heatLoss = heatLoss;
            this.visited = new boolean[4][3];
            this.location = new int[] {y, x};
            this.shortestDistance = Integer.MAX_VALUE / 2;
        }

//        public void setHeuristicValue(int maxX, int maxY, int x , int y) {
//            this.heuristicValue = heatLoss + maxY - y + maxX - x;
//        }
    }

    private static final String puzzleInput = "2023/Day17/test.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static Node getNode(Node[][] nodes, int maxY, int maxX, int y, int x, int facing, int direction) {
        switch (facing) {
            case 0 -> {
                switch (direction) {
                    case 0 -> x--;
                    case 1 -> y--;
                    case 2 -> x++;
                }
            }
            case 1 -> {
                switch (direction) {
                    case 0 -> y--;
                    case 1 -> x++;
                    case 2 -> y++;
                }
            }
            case 2 -> {
                switch (direction) {
                    case 0 -> x++;
                    case 1 -> y++;
                    case 2 -> x--;
                }
            }
            case 3 -> {
                switch (direction) {
                    case 0 -> y++;
                    case 1 -> x--;
                    case 2 -> y--;
                }
            }
        }
        if (x >= 0 && x < maxX && y >= 0 && y < maxY) {
            return nodes[y][x];
        }
        return null;
    }

    public static void printMap(List<String> lines, Node[][] nodes, int maxY, int maxX, Node currentNode) {
        Node startNode = nodes[0][0];
        char[][] map = new char[maxY][maxX];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                map[i][j] = lines.get(i).charAt(j);
            }
        }
        char[] arrows = new char[] {'^', '>', 'v', '<'};
        while (currentNode != startNode) {
            map[currentNode.location[0]][currentNode.location[1]] = arrows[currentNode.direction];
            currentNode = currentNode.prevNode;
        }
        for (char[] ca : map) {
            System.out.println(ca);
        }
        System.out.println();
    }

    public static void addToPQ(PriorityQueue<Node> pqTravel, Node currentNode,
                               Node newNode, int newDirection, boolean front) {
        if (newNode != null && currentNode.shortestDistance + newNode.heatLoss <= newNode.shortestDistance) {
            int blocksTravelled = 1;
            if (front) blocksTravelled = currentNode.blocksTravelled + 1;

//            Node newTravel = new Node(newDirection, blocksTravelled, newNode.location[0], newNode.location[1]);
            newNode.direction = newDirection;
            newNode.prevNode = currentNode;
            newNode.shortestDistance = currentNode.shortestDistance + newNode.heatLoss;
            newNode.blocksTravelled = blocksTravelled;

            if (!newNode.visited[newNode.direction][newNode.blocksTravelled - 1]) pqTravel.add(newNode);
        }
    }

    public static int parseInput(List<String> lines) {
        int maxY = lines.size();
        int maxX = lines.get(0).length();

        Node[][] nodes = new Node[maxY][maxX];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                Node n = new Node(Integer.parseInt("" + lines.get(i).charAt(j)), i, j);
//                n.setHeuristicValue(maxX, maxY);
                nodes[i][j] = n;
            }
        }
        PriorityQueue<Node> pqNode = new PriorityQueue<>(Comparator.comparingInt(a -> a.shortestDistance));

//        Node startNode = nodes[0][0];
//        startNode.shortestDistance = 0;
//        startNode.direction = 1;
//        startNode.blocksTravelled = 1;
//        pqNode.add(startNode);
//        startNode = new Travel(2, 1, 0, 0);
//        startNode.shortestDistance = 0;
//        pqTravel.add(startNode);

        Node startNode = nodes[0][0];
        startNode.direction = 1;
        startNode.blocksTravelled = 1;
        startNode.shortestDistance = 0;
        Node endNode = nodes[maxY - 1][maxX - 1];
        Node currentNode = startNode;
        while (currentNode != null && currentNode != endNode) {
            int[] loc = currentNode.location;

            nodes[loc[0]][loc[1]].visited[currentNode.direction][currentNode.blocksTravelled - 1] = true;
            // 0 left 2 right
            Node left = getNode(nodes, maxY, maxX, loc[0], loc[1], currentNode.direction, 0);
            addToPQ(pqNode, currentNode, left, (currentNode.direction + 3) % 4, false);
            Node right = getNode(nodes, maxY, maxX, loc[0], loc[1], currentNode.direction, 2);
            addToPQ(pqNode, currentNode, right, (currentNode.direction + 1) % 4, false);
            if (currentNode.blocksTravelled < 3) {
                // 1 front
                Node front = getNode(nodes, maxY, maxX, loc[0], loc[1], currentNode.direction, 1);
                addToPQ(pqNode, currentNode, front, currentNode.direction, true);
            }
//            printMap(lines, nodes, maxY, maxX, currentNode);
            currentNode = pqNode.poll();
        }

        int distance = 0;
        char[][] map = new char[maxY][maxX];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                map[i][j] = lines.get(i).charAt(j);
            }
        }
        char[] arrows = new char[] {'^', '>', 'v', '<'};
        while (currentNode.location[0] != 0 && currentNode.location[1] != 1) {
            int[] loc = currentNode.location;
            map[loc[0]][loc[1]] = arrows[currentNode.direction];
            distance += nodes[loc[0]][loc[1]].heatLoss;
            currentNode = currentNode.prevNode;
        }
        for (char[] ca : map) {
            System.out.println(ca);
        }

        // make every point into a node
        // put in Node[][]
        // A*: priority queue based on heuristicvalue
        // start at Node[0][0], make visited, if distance smaller than 3, add nodes to PQ (IF NOT VISITED):
        // front, left, right, and set direction (0, 1, 2, 3) and distance (1 if left, right,
        // +1 if front), set prev + shortestdistance, set heuristicvalue, else if distance bigger than 3, add only left and right
        return nodes[maxY - 1][maxX - 1].shortestDistance;
    }
}