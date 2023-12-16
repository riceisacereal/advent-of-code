import java.util.ArrayList;
import java.util.List;

public class Beam {
    int[] headCoord;
    int direction;

    public Beam(int[] coord, int direction) {
        this.headCoord = coord;
        this.direction = direction;
    }

    public ArrayList<Beam> checkTile(List<String> lines) {
        int x = headCoord[1];
        int y = headCoord[0];

        // Check new direction and if split
        // return this; // Successfully traveled
        // return new Beam; // Split
        // return null; // Hit edge
        ArrayList<Beam> newBeams = new ArrayList<>();
        switch (lines.get(y).charAt(x)) {
            case '\\' -> {
                // Reflect
                // 0 -> 3
                // 1 -> 2
                // 2 -> 1
                // 3 -> 0
                direction = 3 - direction;
                newBeams.add(this);
            }
            case '/' -> {
                // Reflect
                // 0 -> 1
                // 1 -> 0
                // 2 -> 3
                // 3 -> 2
                direction = (2 * (direction / 2)) + (direction + 1) % 2;
                newBeams.add(this);
            }
            case '-' -> {
                if (direction == 1 || direction == 3) {
                    newBeams.add(this);
                } else {
                    // Else split
                    direction = 1;
                    newBeams.add(this);
                    newBeams.add(new Beam(new int[] {headCoord[0], headCoord[1]}, 3));
                }
            }
            case '|' -> {
                if (direction == 0 || direction == 2) {
                    newBeams.add(this);
                } else {
                    // Else split
                    direction = 0;
                    newBeams.add(this);
                    newBeams.add(new Beam(new int[] {headCoord[0], headCoord[1]}, 2));
                }
            }
            default -> newBeams.add(this);
        }
        return newBeams;
    }

    public Beam doUniqueTravel(List<String> lines, int[][][] energized) {
        int maxX = lines.get(0).length();
        int maxY = lines.size();
        int x = headCoord[1];
        int y = headCoord[0];

        // Assign new coordinates
        switch (direction) {
            case 0 -> {
                if (y == 0) return null;
                headCoord[0]--;
            }
            case 1 -> {
                if (x == maxX - 1) return null;
                headCoord[1]++;
            }
            case 2 -> {
                if (y == maxY - 1) return null;
                headCoord[0]++;
            }
            case 3 -> {
                if (x == 0) return null;
                headCoord[1]--;
            }
        }
        // If this direction on this coordinate has already been traveled
        if (energized[headCoord[0]][headCoord[1]][direction] == 1) return null;
        return this;
    }

    public ArrayList<Beam> next(List<String> lines, int[][][] energized) {
        ArrayList<Beam> newBeams = new ArrayList<>();
        for (Beam b : this.checkTile(lines)) {
            Beam newB = b.doUniqueTravel(lines, energized);
            if (newB != null) newBeams.add(newB);
        }
        return newBeams;
    }
}