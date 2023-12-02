import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) throws IOException {
        String puzzleInput = "2023/Day02/input.txt";
        partOne(puzzleInput);
    }

    public static Scanner readFile(String fileName) throws FileNotFoundException {
        FileReader fr = new FileReader(fileName);
        return new Scanner(fr);
    }

    public static int gamePossibility(String line) {
        int redMax = 12;
        int greenMax = 13;
        int blueMax = 14;

        // Split by "Game 1" : "6 red, 8 green, 8 blue; ..."
        String[] game = line.split(": ");
        int gameID = Integer.parseInt(game[0].substring(5));
        // Iterate over "6 red, 8 green, 8 blue"; "4 green";...
        for (String cubeSet : game[1].split("; ")) {
            // Iterate over "6 red", "8 green", "8 blue"
            for (String cubeNum : cubeSet.split(", ")) {
                // Split by "6" "red"
                String[] cubes = cubeNum.split(" ");
                int amount = Integer.parseInt(cubes[0]);

                switch (cubes[1]) {
                    case "red":
                        if (amount > redMax) return 0;
                        break;
                    case "green":
                        if (amount > greenMax) return 0;
                        break;
                    case "blue":
                        if (amount > blueMax) return 0;
                        break;
                    default:
                        throw new IllegalArgumentException("Could not identify: " + cubes[1]);
                }
            }
        }

        return gameID;
    }

    public static int parseInput(Scanner file) {
        int total = 0;
        while(file.hasNextLine()){
            String line = file.nextLine();
            total += gamePossibility(line);
        }

        return total;
    }

    public static void partOne(String puzzleInput) throws IOException {
        Scanner file = readFile(puzzleInput);
        int result = parseInput(file);
        System.out.println(result);
    }
}