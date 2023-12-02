import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PartTwo {
    public static void main(String[] args) throws IOException {
        String puzzleInput = "2023/Day02/input.txt";
        partTwo(puzzleInput);
    }

    public static Scanner readFile(String fileName) throws FileNotFoundException {
        FileReader fr = new FileReader(fileName);
        return new Scanner(fr);
    }

    public static int power(String line) {
        int redMax = 0;
        int greenMax = 0;
        int blueMax = 0;

        String[] game = line.split(": ");
        int gameID = Integer.parseInt(game[0].substring(5));
        String[] grabs = game[1].split("; ");
        for (String cubeSet : grabs) {
            for (String cubeNum : cubeSet.split(", ")) {
                String[] cubes = cubeNum.split(" ");
                int amount = Integer.parseInt(cubes[0]);

                switch (cubes[1]) {
                    case "red" -> redMax = Integer.max(redMax, amount);
                    case "green" -> greenMax = Integer.max(greenMax, amount);
                    case "blue" -> blueMax = Integer.max(blueMax, amount);
                    default -> throw new IllegalArgumentException(
                        "Could not identify: " + cubes[1]);
                }
            }
        }

        return redMax * greenMax * blueMax;
    }

    public static int parseInput(Scanner file) {
        int total = 0;
        while(file.hasNextLine()){
            String line = file.nextLine();
            total += power(line);
        }

        return total;
    }

    public static void partTwo(String puzzleInput) throws IOException {
        Scanner file = readFile(puzzleInput);
        int result = parseInput(file);
        System.out.println(result);
    }
}
