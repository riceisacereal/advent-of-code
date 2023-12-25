import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

//    private static long findTime(ArrayList<Hail> hails) {
//        ArrayList<long[]> hail = new ArrayList<>();
//        for (Hail h : hails) {
//            hail.add(new long[] {h.loc[0], h.vel[0]});
//        }
//
//        long i = 0;
//        while (true) {
//            hail.sort(Comparator.comparingLong(a -> a[0]));
//            long vel = (hail.get(1)[0] - hail.get(0)[0]) / (hail.get(1)[1] - hail.get(0)[1]);
//            i++;
//        }
//        return i;
//    }

//    public static void checkParallel(Hail h1, Hail h2) {
//        double a = (double) h1.vel[0] / (double) h2.vel[0];
//        double b = (double) h1.vel[1] / (double) h2.vel[1];
//        double c = (double) h1.vel[2] / (double) h2.vel[2];
//        if (Math.abs(a - b)< 0.0000001){
//            System.out.println("Found parallel");
//        }
//    }

    public static void getValues() {
        int a, b, c, x, y, z, A, B, C, D, E, F, G, H;
        // 19, 13, 30 @ -2, 1, -2
        // 18, 19, 22 @ -1, -1, -2 //A18 B-1 C19 D-1
        // 20, 25, 34 @ -2, -2, -4 //
        // 12, 31, 28 @ -1, -2, -1
        // 20, 19, 15 @ 1, -5, -3

        // 20, 19, 15 @  1, -5, -3
        a = 24;
        b = 13;
        c = 10;
        x = -3;
        y = 1;
        z = 2;
//        A = 19;
//        B = -2;
//        C = 13;
//        D = 1;
        A = 20;
        B = 1;
        C = 19;
        D = -5;

        E = 18;
        F = -1;
        G = 19;
        H = -1;

        double stage1 = (double) (B * C - A * D + a * D - B * b + A * y - a * y) / (C - b);
        double stage2 = (F * b * C - F * Math.pow(b, 2) + G * B * C - A * D * G + D * G * a - B * G * b
            - F * G * C + F * G * b + H * E * C - H * E * b - H * a * C + H * a * b - B * C * b +
            A * D * b - D * a * b + B * Math.pow(b, 2))
            / (A * b + E * C - E * b - a * C - A * G + G * a);

        System.out.println(stage1);
        System.out.println(stage2);
    }

    public static int parseInput(List<String> lines) {
        ArrayList<Hail> hails = new ArrayList<>();

        for (String line : lines) {
            hails.add(new Hail(line));
        }

        // For every time t >= 0
        // sort on x axis
//        int points = 0;
//        for (int i = 0; i < hails.size() - 1; i++) {
//            for (int j = i + 1; j < hails.size(); j++) {
////                checkParallel(hails.get(i), hails.get(j));
//            }
//        }
        getValues();
        return 0;
    }
}
