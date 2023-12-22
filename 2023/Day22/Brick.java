import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class Brick {
//    int[][] dimension;
    ArrayList<int[]> blocks = new ArrayList<>(); // int[3]
    boolean check;

    public Brick(String line) {
        check = false;
        int[][] dimension = new int[2][3];
        int i = 0;
        int j;
        for (String end : line.split("~")) {
            j = 0;
            for (String n : end.split(",")) {
                dimension[i][j] = Integer.parseInt(n);
                j++;
            }
            i++;
        }

        // Set all blocks
        int[] orientation = new int[] {
            dimension[0][0] - dimension[1][0],
            dimension[0][1] - dimension[1][1],
            dimension[0][2] - dimension[1][2]
        };

        int x = dimension[0][0];
        int y = dimension[0][1];
        int z = dimension[0][2];
        if (orientation[0] != 0) { // x axis oriented
            int s = Math.min(dimension[0][0], dimension[1][0]);
            int e = Math.max(dimension[0][0], dimension[1][0]);
            for (int b = s; b <= e; b++) {
                blocks.add(new int[] {b, y, z});
            }
        } else if (orientation[1] != 0) { // y axis oriented
            int s = Math.min(dimension[0][1], dimension[1][1]);
            int e = Math.max(dimension[0][1], dimension[1][1]);
            for (int b = s; b <= e; b++) {
                blocks.add(new int[] {x, b, z});
            }
        } else { // z axis oriented or single block
            int s = Math.min(dimension[0][2], dimension[1][2]);
            int e = Math.max(dimension[0][2], dimension[1][2]);
            for (int b = s; b <= e; b++) {
                blocks.add(new int[] {x, y, b});
            }
        }
    }

    public void lowerBrickBy(int n) {
        for (int[] block : blocks) {
            block[2] -= n;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Brick brick = (Brick) o;
        return check == brick.check && blocks.equals(brick.blocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blocks, check);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int[] block : blocks) {
            s.append(Arrays.toString(block));
        }
        return s.toString();
    }
}
