import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String puzzleInput = "Day01/input.txt";
//        partOne(puzzleInput);
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
            stringArray = file.nextLine().replaceAll("[a-z]", "").split("");
            sumCalibration += Integer.parseInt(stringArray[0] + stringArray[stringArray.length - 1]);
        }

        System.out.println(sumCalibration);
    }
    /*
    1 o e
    2 t o
    3 t e
    4 f r
    5 f e
    6 s x
    7 s n
    8 e t
    9 n e

    21 twone
    82 eightwo
    83 eighthree
    79 sevenine
    18 oneight
    38 threeight
    58 fiveight
    98 nineight
    * */

    public static void partTwo(String puzzleInput) throws IOException {
        Scanner file = readFile(puzzleInput);
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
            stringArray = line.replaceAll("[a-z]", "").split("");
            sumCalibration += Integer.parseInt(stringArray[0] + stringArray[stringArray.length - 1]);
        }

        System.out.println(sumCalibration);
    }
}
