import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PartOne {
    private static final String puzzleInput = "2023/Day11/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        long result = partTwo(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static boolean allEmptySpace(ArrayList<Character> row) {
        for (char c : row) {
            if (c == '#') return false;
        }
        return true;
    }

    public static boolean allEmptySpace(ArrayList<ArrayList<Character>> expandedUniverse, int col) {
        for (ArrayList<Character> ca : expandedUniverse) {
            if (ca.get(col) == '#') return false;
        }
        return true;
    }

    public static boolean allEmptySpace(List<String> lines, int col) {
        for (String s : lines) {
            if (s.charAt(col) == '#') return false;
        }
        return true;
    }

    public static ArrayList<Character> getEmptyRow(int cols) {
        return new ArrayList<>(Collections.nCopies(cols, '.'));
    }

    public static ArrayList<ArrayList<Character>> getExpandedUniverse(List<String> lines) {
        int rows = lines.size();
        int cols = lines.get(0).length();

        ArrayList<ArrayList<Character>> expandedUniverse = new ArrayList<>();
        for (String line : lines) {
            ArrayList<Character> l = new ArrayList<>();
            for (char c : line.toCharArray()) {
                l.add(c);
            }
            expandedUniverse.add(l);
        }

        int rowOffset = 0;
        for (int row = 0; row < rows; row++) {
            int actualIndex = row + rowOffset;
            if (allEmptySpace(expandedUniverse.get(actualIndex))) {
                expandedUniverse.add(actualIndex, getEmptyRow(cols));
                rowOffset++;
            }
        }

        int colOffset = 0;
        for (int col = 0; col < cols; col++) {
            int actualIndex = col + colOffset;
            if (allEmptySpace(expandedUniverse, actualIndex)) {
                for (ArrayList<Character> ca : expandedUniverse) {
                    ca.add(actualIndex, '.');
                }
                colOffset++;
            }
        }

        return expandedUniverse;
    }

    public static long partTwo(List<String> lines) {
        int rows = lines.size();
        int cols = lines.get(0).length();

        ArrayList<int[]> galaxyCoordinates = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char c = lines.get(row).charAt(col);
                if (c == '#') {
                    galaxyCoordinates.add(new int[] {row, col});
                }
            }
        }

        int expand = 1000000 - 1;

        int rowOffset = 0;
        for (int row = 0; row < rows; row++) {
            int newIndex = row + rowOffset;
            if (lines.get(row).replaceAll("#", "").length() == cols) {
                for (int[] co : galaxyCoordinates) {
                    if (co[0] > newIndex) {
                        co[0] += expand;
                    }
                }
                rowOffset += expand;
            }
        }

        int colOffset = 0;
        for (int col = 0; col < cols; col++) {
            int newIndex = col + colOffset;
            if (allEmptySpace(lines, col)) {
                for (int[] co : galaxyCoordinates) {
                    if (co[1] > newIndex) {
                        co[1] += expand;
                    }
                }
                colOffset += expand;
            }
        }

        long distances = 0;
        for (int n = 0; n < galaxyCoordinates.size(); n++) {
            if (n == galaxyCoordinates.size() - 1) {
                break;
            }

            int[] thisGalaxy = galaxyCoordinates.get(n);
            for (int next = n + 1; next < galaxyCoordinates.size(); next++) {
                int[] nextGalaxy = galaxyCoordinates.get(next);
                distances += Math.abs(thisGalaxy[0] - nextGalaxy[0])
                    + Math.abs(thisGalaxy[1] - nextGalaxy[1]);
            }
        }

        return distances;
    }

//    public static int parseInput(List<String> lines) {
//        ArrayList<ArrayList<Character>> expandedUniverse = getExpandedUniverse(lines);
//        int rows = expandedUniverse.size();
//        int cols = expandedUniverse.get(0).size();
//
//        ArrayList<int[]> galaxyCoordinates = new ArrayList<>();
//        for (int row = 0; row < rows; row++) {
//            for (int col = 0; col < cols; col++) {
//                char c = expandedUniverse.get(row).get(col);
//                if (c == '#') {
//                    galaxyCoordinates.add(new int[] {row, col});
//                }
//            }
//        }
//
//        int distances = 0;
//        for (int n = 0; n < galaxyCoordinates.size(); n++) {
//            if (n == galaxyCoordinates.size() - 1) {
//                break;
//            }
//
//            int[] thisGalaxy = galaxyCoordinates.get(n);
//            for (int next = n + 1; next < galaxyCoordinates.size(); next++) {
//                int[] nextGalaxy = galaxyCoordinates.get(next);
//                distances += Math.abs(thisGalaxy[0] - nextGalaxy[0])
//                    + Math.abs(thisGalaxy[1] - nextGalaxy[1]);
//            }
//        }
//
//        for (String line : lines) {
//            System.out.println(line);
//        }
//
//        return distances;
//    }
}