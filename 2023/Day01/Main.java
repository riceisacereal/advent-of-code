import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String puzzleInput = "Day01/input.txt";
        partOne(puzzleInput);
        partTwo(puzzleInput);
    }

    public static Scanner readFile(String fileName) throws FileNotFoundException {
        FileReader fr = new FileReader(fileName);
        return new Scanner(fr);
    }

    public static void partOne(String puzzleInput) throws IOException {
        Scanner file = readFile(puzzleInput);

        String[] stringArray;
        int sumCalibration = 0;
        while(file.hasNextLine()){
            // Remove all lower case characters and split up all the numbers
            stringArray = file.nextLine().replaceAll("[a-z]", "").split("");
            // Combine first and last numbers
            sumCalibration += Integer.parseInt(stringArray[0] + stringArray[stringArray.length - 1]);
        }

        System.out.println(sumCalibration);
    }

    public static void partTwo(String puzzleInput) throws IOException {
        Scanner file = readFile(puzzleInput);
        /*
            Since numbers can repeatedly morph with each other, just leave the overlapping (first and
            last characters) in the line
        */
        String[][] numberReplacement = {
            {"one", "o1e"},
            {"two", "t2o"},
            {"three", "t3e"},
            {"four", "f4r"},
            {"five", "f5e"},
            {"six", "s6x"},
            {"seven", "s7n"},
            {"eight", "e8t"},
            {"nine", "n9e"}
        };

        String[] stringArray;
        int sumCalibration = 0;
        String line;
        while(file.hasNextLine()){
            line = file.nextLine();
            for (String[] r : numberReplacement) {
                line = line.replaceAll(r[0], r[1]);
            }
            // Remove all lower case characters and split up all the numbers
            stringArray = line.replaceAll("[a-z]", "").split("");
            // Combine first and last numbers
            sumCalibration += Integer.parseInt(stringArray[0] + stringArray[stringArray.length - 1]);
        }

        System.out.println(sumCalibration);
    }
}