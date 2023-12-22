import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PartOne {
    private static final String puzzleInput = "2023/Day22/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int parseInput(List<String> lines) {
        ArrayList<Brick> bricks = new ArrayList<>();

        int maxX = 0;
        int maxY = 0;
        for (String line : lines) {
            Brick b = new Brick(line);
            bricks.add(b);
            // Determine dimensions for hTrack
            maxX = Math.max(maxX, b.blocks.get(b.blocks.size() - 1)[0]);
            maxY = Math.max(maxY, b.blocks.get(b.blocks.size() - 1)[1]);
        }

        bricks.sort(Comparator.comparingInt(a -> a.blocks.get(0)[2]));
        int[][] hTrack = new int[maxX + 1][maxY + 1]; // Keep track of highest z index looking down
        for (int i = 0; i <= maxX; i++) {
            for (int j = 0; j <= maxY; j++) {
                hTrack[i][j] = 1; // Initialize with 1 for earliest placeable z index
            }
        }

        // Drop blocks
        for (Brick b : bricks) {
            // Figure out how low to drop the brick
            // If horizontal: drop z to max z for every block
            // If vertical: drop z to z of xy coordinate

            // Change coordinates of the brick
            int dropTo = 0;
            int lowestBlock = b.blocks.get(0)[2];
            for (int[] block : b.blocks) {
                dropTo = Math.max(dropTo, hTrack[block[0]][block[1]]);
                lowestBlock = Math.min(lowestBlock, block[2]);
            }
            b.lowerBrickBy(lowestBlock - dropTo);
            // Update hTrack
            for (int[] block : b.blocks) {
                hTrack[block[0]][block[1]] = block[2] + 1;
            }
        }

        // Find highest Z
        int maxZ = 0;
        for (Brick b : bricks) {
            maxZ = Math.max(maxX, b.blocks.get(b.blocks.size() - 1)[2]);
        }
        Brick[][][] monolith = new Brick[maxX + 2][maxY + 2][maxZ + 2]; // One more empty layer for easier top layer check
        // Paint bricks in monolith
        for (Brick b : bricks) {
            for (int[] block : b.blocks) {
                monolith[block[0]][block[1]][block[2]] = b;
            }
        }

        // Iterate through all blocks, for every brick, check the bricks under it
        HashSet<Brick> disintegrate = new HashSet<>();
        HashSet<Brick> keep = new HashSet<>();
        for (Brick b : bricks) {
            // Check under
            HashSet<Brick> supportingBricks = new HashSet<>();
            for (int[] block : b.blocks) {
                Brick under = monolith[block[0]][block[1]][block[2] - 1];
                if (under != null && under != b) { // Prevent adding itself when vertical
                    supportingBricks.add(under);
                }
            }

            if (supportingBricks.size() == 1) {
                // If only 1 brick under it, can definitely NOT disintegrate, add to set of keep
                keep.addAll(supportingBricks);
            } else if (supportingBricks.size() > 1) {
                // If more than 1 brick under it, add all to set of bricks that can be disintegrated
                disintegrate.addAll(supportingBricks);
            } else {
                if (b.blocks.get(0)[2] != 1) { // If on ground, no blocks are under it
                    System.out.println("No Bricks supporting, error in dropping code");
                }
            }

            // Check above it, if no blocks above add itself to set of bricks
            HashSet<Brick> raisedBricks = new HashSet<>();
            for (int[] block : b.blocks) {
                Brick up = monolith[block[0]][block[1]][block[2] + 1];
                if (up != null && up != b) { // Prevent adding itself when vertical
                    raisedBricks.add(up);
                }

                if (raisedBricks.isEmpty()) disintegrate.add(b);
            }
        }

        // Loop through keep, remove each one from disintegrate
        for (Brick b : keep) {
            disintegrate.remove(b);
        }

//        for (Brick b : bricks) {
//            System.out.println(b);
//        }

        return disintegrate.size();
    }
}
