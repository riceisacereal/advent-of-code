import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class PartTwo {
    private static final String puzzleInput = "2023/Day19/input.txt";

    public static void main(String[] args) throws IOException {
        Scanner file = readFile(puzzleInput);
        long result = parseInput(file);
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

    public static void processConds(Queue<Tuple> q, ArrayList<Condition> conds, Part p) {
        for (Condition con : conds) {
            // Split new one if condition true and change current part to go until the end
            switch (con.condType) {
                case 2 -> q.add(new Tuple(con.result, p));
                case 0 -> { // Less than
                    int[] newRange = new int[2];
                    newRange[0] = p.xmasRange.get(con.xmas)[0];
                    newRange[1] = con.thresh - 1;
                    if (newRange[1] - newRange[0] <= 0) continue;
                    q.add(new Tuple(con.result, Part.copyPart(p, con.xmas, newRange)));

                    p.xmasRange.get(con.xmas)[0] = con.thresh;
                    if (p.xmasRange.get(con.xmas)[1] - p.xmasRange.get(con.xmas)[0] <= 0) return;
                }
                case 1 -> { // Larger than
                    int[] newRange = new int[2];
                    newRange[0] = con.thresh + 1;
                    newRange[1] = p.xmasRange.get(con.xmas)[1];
                    if (newRange[1] - newRange[0] <= 0) continue;
                    q.add(new Tuple(con.result, Part.copyPart(p, con.xmas, newRange)));

                    p.xmasRange.get(con.xmas)[1] = con.thresh;
                    if (p.xmasRange.get(con.xmas)[1] - p.xmasRange.get(con.xmas)[0] <= 0) return;
                }
            }
        }
    }

    public static long parseInput(Scanner file) {
        HashMap<String, Workflow> workflows = getWorkflows(file);
//        ArrayList<Part> parts = getParts(file);
        // Make Queue where workflow and part are a pair
        // Part has a range within
        // BFS and add to queue
        Queue<Tuple> q = new LinkedList<>();
        Part start = new Part(new int[] {1, 4000},
            new int[] {1, 4000},
            new int[] {1, 4000},
            new int[] {1, 4000});
        q.add(new Tuple("in", start));

        long count = 0;
        while (!q.isEmpty()) {
            Tuple current = q.poll();
            String wfName = current.wfName;
            Part p = current.p;
            if (wfName.equals("A")) {
                count += p.getProductOfPossibleXmas();
                continue;
            } else if (wfName.equals("R")) {
                continue;
            }

            Workflow wf = workflows.get(wfName);
            processConds(q, wf.conds, p);
        }

//        int accepted = 0;
//        Workflow in = workflows.get("in");
//        for (Part p : parts) {
//            Workflow current = in;
//            String result = current.getResult(p);
//            while (!result.equals("R")) {
//                result = current.getResult(p);
//                if (result.equals("A")) {
//                    accepted += p.getSumOfXmas();
//                    break;
//                }
//                current = workflows.get(result);
//            }
//        }

        return count;
    }
}
