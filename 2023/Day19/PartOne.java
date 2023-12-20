import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class PartOne {
    private static final String puzzleInput = "2023/Day19/input.txt";

    public static void main(String[] args) throws IOException {
        Scanner file = readFile(puzzleInput);
        int result = parseInput(file);
        System.out.println(result);
    }

    public static Scanner readFile(String fileName) throws FileNotFoundException {
        FileReader fr = new FileReader(fileName);
        return new Scanner(fr);
    }

    public static HashMap<String, Workflow> getWorkflows(Scanner file) {
        HashMap<String, Workflow> workflows = new HashMap<>();
        String line = file.nextLine();
        while (!line.isBlank()) {
            Workflow wf = new Workflow(line);
            workflows.put(wf.name, wf);
            line = file.nextLine();
        }
        return workflows;
    }

    public static ArrayList<Part> getParts(Scanner file) {
        ArrayList<Part> parts = new ArrayList<>();
        while (file.hasNext()) {
            String line = file.nextLine();
            Part p = new Part(line);
            parts.add(p);
        }
        return parts;
    }

    public static int parseInput(Scanner file) {
        HashMap<String, Workflow> workflows = getWorkflows(file);
        ArrayList<Part> parts = getParts(file);

        int accepted = 0;
        Workflow in = workflows.get("in");
        for (Part p : parts) {
            Workflow current = in;
            String result = current.getResult(p);
            while (!result.equals("R")) {
                result = current.getResult(p);
                if (result.equals("A")) {
                    accepted += p.getSumOfXmas();
                    break;
                }
                current = workflows.get(result);
            }
        }

        return accepted;
    }
}
