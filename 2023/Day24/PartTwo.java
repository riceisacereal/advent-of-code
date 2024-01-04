import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class PartTwo {
    private static final String puzzleInput = "2023/Day24/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static boolean checkOrder(ArrayList<Hail> initialOrder, ArrayList<Hail> hails) {
        for (int i = 0; i < hails.size(); i++) {
            if (initialOrder.get(i) != hails.get(i)) {
                 return false;
            }
        }
        return true;
    }

    public static int parseInput(List<String> lines) {
        ArrayList<Hail> hails = new ArrayList<>();

        for (String line : lines) {
            hails.add(new Hail(line));
        }

        // Remove ones with the same coefficient
//        HashSet<Integer> xCoefficient = new HashSet<>();
//        int lastIndex = 0;
//        boolean removed = true;
//        while (removed) {
//            removed = false;
//            for (int i = lastIndex; i < hails.size(); i++) {
//                Hail h = hails.get(i);
//                if (xCoefficient.contains(h.vel[0])) {
//                    hails.remove(h);
//                    lastIndex = i;
//                    removed = true;
//                    break;
//                } else {
//                    xCoefficient.add(h.vel[0]);
//                }
//            }
//        }

        for (Hail h : hails) {
            h.loc[0] += 100L * h.vel[0];
        }
        hails.sort(Comparator.comparingLong(a -> a.loc[0]));
        ArrayList<Hail> initialOrder = new ArrayList<>(hails);
        ArrayList<Hail> reverseOrder = new ArrayList<>(hails);
        Collections.reverse(reverseOrder);

        // Check order for one axis
        long before = 0;
        long after = 0;
        long t = 1000000;
//        while (true) {
//            for (Hail h : hails) {
//                h.loc[0] += h.vel[0];
//            }
//            hails.sort(Comparator.comparingLong(a -> a.loc[0]));
//            if (checkOrder(initialOrder, hails)) {
//                before = t;
//            } else {
//                System.out.println(before + " " +  t);
//                break;
//            }
////            } else if (checkOrder(reverseOrder, hails)) {
////                after = t;
////                break;
////            }
//            t++;
//        }

        System.out.println(108375683349444L + 220656145109505L + 289502736377988L);
        // 11165357 is when order changes
        System.out.println(before + " " + after);

        // For every time t >= 0
        // sort on x axis
//        int points = 0;
//        for (int i = 0; i < hails.size() - 1; i++) {
//            for (int j = i + 1; j < hails.size(); j++) {
////                checkParallel(hails.get(i), hails.get(j));
//            }
//        }
        return 0;
    }
}
