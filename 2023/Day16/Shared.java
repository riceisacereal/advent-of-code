import java.util.List;
import java.util.Stack;

public class Shared {
    public static void markEnergized(int[][][] energized, Stack<Beam> beams) {
        for (Beam b : beams) {
            energized[b.headCoord[0]][b.headCoord[1]][b.direction] = 1;
        }
    }
    
    public static int reflectBeams(List<String> lines, Beam startingBeam) {
        int maxX = lines.get(0).length();
        int maxY = lines.size();

        // Make int array to mark energized blocks
        int[][][] energized = new int[maxY][maxX][4];

        Stack<Beam> beams = new Stack<>();
        beams.push(startingBeam);
        while (!beams.isEmpty()) {
            markEnergized(energized, beams);

            Stack<Beam> newBeams = new Stack<>();
            while (!beams.isEmpty()) {
                Beam b = beams.pop();
                for (Beam newB : b.next(lines, energized)) {
                    newBeams.push(newB);
                }
            }
            beams = newBeams;
        }

        int count = 0;
        for (int[][] i : energized) {
            for (int[] j : i) {
                for (int k : j) {
                    if (k == 1) {
                        count++;
                        break;
                    }
                }
            }
        }
        return count;
    }
}
