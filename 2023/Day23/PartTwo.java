import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

// 5018 too low
// 6389 too high

public class PartTwo {
    private static final String puzzleInput = "2023/Day23/input.txt";
    private static final int[][] displace = new int[][] {
        {0, -1},
        {0, 1},
        {-1, 0},
        {1, 0}
    };

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int findLongestDistance(Stack<Node> path, Node current, Node end, int distance) {
        // If the neighbour to the end is reached, must go to end, otherwise end will be unreachable
        if (current == end.getFirstNeighbour()) {
            return distance + end.connectedNodes.get(end.getFirstNeighbour());
        }

        ArrayList<Integer> distanceToEnd = new ArrayList<>();
        for (Map.Entry<Node, Integer> entry : current.connectedNodes.entrySet()) {
            Node next = entry.getKey();
            if (!path.contains(next)) {
                path.push(next);
                distanceToEnd.add(findLongestDistance(path, next, end, distance + entry.getValue()));
                path.pop();
            }
        }

        if (distanceToEnd.size() == 0) {
            return 0;
        } else {
            return Collections.max(distanceToEnd);
        }
    }

    public static void mapNodes(List<String> lines, Node[][] nodeMap, Queue<int[]> q) {
        int maxY = nodeMap.length;
        int maxX = nodeMap[0].length;

        boolean[][] visited = new boolean[maxY][maxX];
        while (!q.isEmpty()) {
            int[] loc = q.poll();
            visited[loc[0]][loc[1]] = true;

            int count = 0;
            ArrayList<int[]> nextLocs = new ArrayList<>();
            for (int[] d : displace) {
                int y = loc[0] + d[0];
                int x = loc[1] + d[1];
                if (x >= 0 && x < maxX && y >= 0 && y < maxY && lines.get(y).charAt(x) != '#') {
                    // Count up directions it can go
                    count++;
                    if (!visited[y][x]) nextLocs.add(new int[] {y, x});
                }
            }

            if (count > 2 && nodeMap[loc[0]][loc[1]] == null) {
                nodeMap[loc[0]][loc[1]] = new Node(loc);
            }

            q.addAll(nextLocs);
        }
    }

    public static Edge getNextNode(List<String> lines, Node[][] nodeMap, boolean[][] visited, int[] current) {
        int maxY = nodeMap.length;
        int maxX = nodeMap[0].length;

        int distance = 1;
        while (nodeMap[current[0]][current[1]] == null) {
            visited[current[0]][current[1]] = true;
            for (int[] d : displace) {
                // Check if can go this direction
                int y = current[0] + d[0];
                int x = current[1] + d[1];
                if (x >= 0 && x < maxX && y >= 0 && y < maxY && lines.get(y).charAt(x) != '#') {
                    if (!visited[y][x]) {
                        current[0] = y;
                        current[1] = x;
                        distance++;
                        break;
                    }
                }
            }
        }

        return new Edge(nodeMap[current[0]][current[1]], distance);
    }

    public static void connectEdges(List<String> lines, Node[][] nodeMap) {
        int maxY = nodeMap.length;
        int maxX = nodeMap[0].length;

        for (Node[] na : nodeMap) {
            for (Node n : na) {
                if (n == null) continue;
                int[] loc = n.coord;

                // For every direction
                for (int[] d : displace) {
                    // Check if can go this direction
                    int y = loc[0] + d[0];
                    int x = loc[1] + d[1];
                    if (x >= 0 && x < maxX && y >= 0 && y < maxY && lines.get(y).charAt(x) != '#') {
                        int[] current = new int[] {y, x};
                        boolean[][] visited = new boolean[maxY][maxX];
                        visited[loc[0]][loc[1]] = true;
                        // Find connecting edge (node and distance) in that direction
                        Edge e = getNextNode(lines, nodeMap, visited, current);
                        // Connect edges
                        e.node.connectedNodes.put(n, e.length);
                        n.connectedNodes.put(e.node, e.length);
                    }
                }
            }
        }
    }

    public static int parseInput(List<String> lines) {
        int maxY = lines.size();
        int maxX = lines.get(0).length();

        Node start = new Node(new int[] {0, 1});
        Node end = new Node(new int[] {maxY - 1, maxX - 2});

        Node[][] nodeMap = new Node[maxY][maxX];
        nodeMap[0][1] = start;
        nodeMap[maxY - 1][maxX - 2] = end;

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] {0, 1}); // Add start

        mapNodes(lines, nodeMap, q); // Identify all nodes
        connectEdges(lines, nodeMap); // Link all nodes

        // For node map find longest distance
        Stack<Node> longestDistance = new Stack<>();
        longestDistance.add(start);

        return findLongestDistance(longestDistance, start, end, 0);
    }
}
