import java.util.PriorityQueue;

public class Shared {
    public static int manhattenDistance(int[] thisLoc, int[] max) {
        return max[0] - thisLoc[0] + max[1] - thisLoc[1];
    }

    public static int[] getNewLoc(int direction, int[] loc, int[] max) {
        int x = loc[1];
        int y = loc[0];
        switch (direction) {
            case 0 -> y--;
            case 1 -> x++;
            case 2 -> y++;
            case 3 -> x--;
        }
        if (x >= 0 && x < max[1] && y >= 0 && y < max[0]) {
            return new int[] {y, x};
        }
        return null;
    }

    public static void addToQueue(PriorityQueue<QueueElement> pq, int[][] heatLoss,
                                  boolean[][][][] visited, int[] loc, int direction, int steps, QueueElement current) {
        int[] max = new int[] {heatLoss.length, heatLoss[0].length};
        int[] end = new int[] {max[0] - 1, max[1] - 1};
        int[] newLoc = getNewLoc(direction, loc, max);
        if (newLoc != null) {
            // don't visit if visited
            int hl = heatLoss[newLoc[0]][newLoc[1]];
            if (!visited[newLoc[0]][newLoc[1]][direction][steps]) {
                pq.add(new QueueElement(newLoc, direction, steps, current.distance + hl,
                    hl + manhattenDistance(newLoc, end)));
            }
        }
    }
}
