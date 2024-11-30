import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

// 5018 too low
// 6389 too high
// I don't remember what I was trying to do anymore but I used an equation solver for part 2

public class PartTwo {
    private static final String puzzleInput = "2023/Day24/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        long result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static Hail checkAndSetValues(ArrayList<Hail> hails, int x, int y, int z) {
        // Trajectory of rock: L = (a, b, c) + lambda(x, y, z)
        Hail one = hails.get(0);
        long A = one.loc[0];
        int B = one.vel[0];
        long C = one.loc[1];
        int D = one.vel[1];

        long Q = one.loc[2];
        int R = one.vel[2];

        Hail two = hails.get(1);
        long E = two.loc[0];
        int F = two.vel[0];
        long G = two.loc[1];
        int H = two.vel[1];

        long S = two.loc[2];
        int T = two.vel[2];

        Hail three = hails.get(2);
        long I = three.loc[0];
        int J = three.vel[0];
        long K = three.loc[1];
        int L = three.vel[1];

        long U = three.loc[2];
        int V = three.vel[2];

        Hail four = hails.get(3);
        long M = four.loc[0];
        int N = four.vel[0];
        long O = four.loc[1];
        int P = four.vel[1];

        long W = four.loc[2];
        int X = four.vel[2];

        if ((B*H - B*y - H*x + x*y - F*D + F*y + D*x - x*y) == 0) return null;
        long b = ((G*x - F*G + H*E - E*y) * (D - y) - (C*x - B*C + A*D - A*y) * (H - y)) /
            (B*H - B*y - H*x + x*y - F*D + F*y + D*x - x*y);
        if (H == y) return null;
        long a = (G*x - F*G + H*E - E*y - b*x + F*b) / (H - y);
        // Check for remaining hails
        if (K * (x - J) + L*(I - a) != b * (x - J) + y*(I - a) || O * (x - N) + P*(M - a) != b * (x - N) + y*(M - a)) {
            return null;
        }
        // Check for all c values
        if (D == y || H == y || L == y || P == y) return null;
        long c1 = (C*z - R*C + D*Q - b*z + R*b - Q*y) / (D - y); // Divide by 0 error in test input but shouldn't be a problem in real input
        long c2 = (G*z - T*G + H*S - b*z + T*b - S*y) / (H - y);
        long c3 = (K*z - V*K + L*U - b*z + V*b - U*y) / (L - y);
        long c4 = (O*z - X*O + P*W - b*z + X*b - W*y) / (P - y);

        if (c1 == c2 && c2 == c3 && c3 == c4) {
            return new Hail(new long[] {a, b, c4}, new int[] {x, y, z});
        }

        return null;
    }

    public static long parseInput(List<String> lines) {
        // Read in first 4 hails
        ArrayList<Hail> hails = new ArrayList<>();

        for (String line : lines) {
            hails.add(new Hail(line));
        }

        checkAndSetValues(hails, 277, -27, 121);

        int i = 0;
        for (int x = -500; x <= 500; x++) { // Rough guess of 500 max velocity
            for (int y = -500; y <= 500; y++) {
                for (int z = -500; z <= 500; z++) {
                    i++;
                    if (i % 10000000 == 0) {
                        System.out.println(i);
                    }
                    Hail rock = checkAndSetValues(hails, x, y, z);
                    if (rock != null) {
                        return rock.loc[0] + rock.loc[1] + rock.loc[2];
                    }
                }
            }
        }

        return -1;
    }
}
