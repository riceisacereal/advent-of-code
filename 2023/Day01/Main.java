import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner file = readFile("Day01/1.txt");
        String[] stringArray;
        int sumCalibration = 0;
        while(file.hasNextLine()){
            stringArray = file.nextLine().replaceAll("[a-z]", "").split("");
            sumCalibration += Integer.parseInt(stringArray[0] + stringArray[stringArray.length - 1]);
        }
        System.out.println(sumCalibration);
    }

    public static Scanner readFile(String fileName) throws FileNotFoundException {
        FileReader fr = new FileReader(fileName);
        return new Scanner(fr);
    }
}
