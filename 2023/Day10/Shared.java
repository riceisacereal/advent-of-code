import static java.util.Map.entry;

import java.util.List;
import java.util.Map;

public class Shared {
    public static final int[][] directionDisplacement = new int[][] {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    // The following two variables are only needed for determining the starting symbol
    public static final String[] connectingShapes = new String[] {"|7F", "-J7", "|JL", "-LF"};
    public static final Map<String, Character> connectionToSymbol = Map.ofEntries(
        entry("02", '|'),
        entry("13", '-'),
        entry("01", 'L'),
        entry("12", 'F'),
        entry("23", '7'),
        entry("03", 'J')
    );
    public static final Map<Character, Character> betterMapRep = Map.ofEntries(
        entry('|', '│'),
        entry('-', '─'),
        entry('J', '┘'),
        entry('7', '┐'),
        entry('L', '└'),
        entry('F', '┌')
    );

    public static char getStartChar(int y, int x, List<String> lines) {
        StringBuilder connection = new StringBuilder();
        int maxX = lines.get(0).length();
        int maxY = lines.size();
        for (int i = 0; i < 4; i++) {
            int newX = x + directionDisplacement[i][1];
            int newY = y + directionDisplacement[i][0];
            if (newX >= 0 && newX < maxX && newY >= 0 && newY < maxY) {
                char currentChar = lines.get(newY).charAt(newX);
                if (connectingShapes[i].contains(currentChar + "")) {
                    connection.append(i);
                }
            }
        }
        return connectionToSymbol.get(connection.toString());
    }

    public static void printMap(String[] convertedLines) {
        for (String line : convertedLines) {
            String s = line
                .replaceAll("\\|", "│")
                .replaceAll("-", "─")
                .replaceAll("J", "┘")
                .replaceAll("7", "┐")
                .replaceAll("L", "└")
                .replaceAll("F", "┌");
            System.out.println(s);
        }
    }

    public static void printMap(char[][] convertedLines) {
        for (char[] line : convertedLines) {
            String s = new String(line)
                .replaceAll("\\|", "│")
                .replaceAll("-", "─")
                .replaceAll("J", "┘")
                .replaceAll("7", "┐")
                .replaceAll("L", "└")
                .replaceAll("F", "┌");
            System.out.println(s);
        }
    }
}
