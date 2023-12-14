import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PartTwo {
    private static final String puzzleInput = "2023/Day14/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    static class Board {
        private ArrayList<char[]> tilted;

        public Board(List<String> lines) {
            this.tilted = new ArrayList<>();
            for (String line : lines) {
                this.tilted.add(line.toCharArray());
            }
        }

        public Board(ArrayList<char[]> tilted) {
            this.tilted = tilted;
        }

        public void tiltNorth() {
            int maxY = this.tilted.size();
            int maxX = this.tilted.get(0).length;
            int[] northTrack = new int[maxX];

            for (int i = 0; i < maxY; i++) {
                char[] line = this.tilted.get(i);
                for (int j = 0; j < maxX; j++) {
                    char c = line[j];
                    if (c == '#') northTrack[j] = i + 1;
                    else if (c == 'O') {
                        this.tilted.get(i)[j] = '.';
                        this.tilted.get(northTrack[j])[j] = 'O';
                        northTrack[j]++;
                    }
                }
            }
        }

        public void tiltSouth() {
            int maxY = this.tilted.size();
            int maxX = this.tilted.get(0).length;
            int[] southTrack = new int[maxX];
            Arrays.fill(southTrack, maxY - 1);

            for (int i = maxY - 1; i >= 0; i--) {
                char[] line = this.tilted.get(i);
                for (int j = maxX - 1; j >= 0; j--) {
                    char c = line[j];
                    if (c == '#') southTrack[j] = i - 1;
                    else if (c == 'O') {
                        this.tilted.get(i)[j] = '.';
                        this.tilted.get(southTrack[j])[j] = 'O';
                        southTrack[j]--;
                    }
                }
            }
        }

        public void tiltWest() {
            int maxY = this.tilted.size();
            int maxX = this.tilted.get(0).length;
            int[] westTrack = new int[maxY];

            for (int j = 0; j < maxX; j++) {
                for (int i = 0; i < maxY; i++) {
                    char c = this.tilted.get(i)[j];
                    if (c == '#') westTrack[i] = j + 1;
                    else if (c == 'O') {
                        this.tilted.get(i)[j] = '.';
                        this.tilted.get(i)[westTrack[i]] = 'O';
                        westTrack[i]++;
                    }
                }
            }
        }

        public void tiltEast() {
            int maxY = this.tilted.size();
            int maxX = this.tilted.get(0).length;
            int[] eastTrack = new int[maxY];
            Arrays.fill(eastTrack, maxX - 1);

            for (int j = maxX - 1; j >= 0; j--) {
                for (int i = maxY - 1; i >= 0; i--) {
                    char c = this.tilted.get(i)[j];
                    if (c == '#') eastTrack[i] = j - 1;
                    else if (c == 'O') {
                        this.tilted.get(i)[j] = '.';
                        this.tilted.get(i)[eastTrack[i]] = 'O';
                        eastTrack[i]--;
                    }
                }
            }
        }

        public Board deepCopy() {
            ArrayList<char[]> a = new ArrayList<>();
            for (char[] ca : this.tilted) {
                char[] newCa = new char[ca.length];
                System.arraycopy(ca, 0, newCa, 0, ca.length);
                a.add(newCa);
            }
            return new Board(a);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Board board = (Board) o;
            for (int i = 0; i < this.tilted.size(); i++) {
                if (!Arrays.equals(this.tilted.get(i), board.tilted.get(i))) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Objects.hash(tilted);
        }
    }

    public static int findLoop(Board tilted) {
        ArrayList<Board> boards = new ArrayList<>();
        int count = 0;
        while (true) {
            tilted.tiltNorth();
            tilted.tiltWest();
            tilted.tiltSouth();
            tilted.tiltEast();

            for (int i = 0; i < boards.size(); i++) {
                Board b = boards.get(i);
                if (b.equals(tilted)) {
                    return i + ((1000000000 - i) % (count - i));
                }
            }
            boards.add(tilted.deepCopy());
            count++;
        }
    }

    public static int parseInput(List<String> lines) {
        int maxY = lines.size();
        int maxX = lines.get(0).length();
        Board tilted = new Board(lines);

        int loops = findLoop(tilted);
        Board freshBoard = new Board(lines);
        for (int k = 0; k < loops; k++) {
            freshBoard.tiltNorth();
            freshBoard.tiltWest();
            freshBoard.tiltSouth();
            freshBoard.tiltEast();
        }

        int totalLoad = 0;
        for (int i = 0; i < maxY; i++) {
            char[] line = freshBoard.tilted.get(i);
            for (int j = 0; j < maxX; j++) {
                char c = line[j];
                if (c == 'O') {
                    totalLoad += maxY - i;
                }
            }
        }

        return totalLoad;
    }
}
