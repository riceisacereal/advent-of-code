import java.util.Arrays;
import java.util.HashMap;

public class Part {
    HashMap<String, Integer> xmas;
    HashMap<String, int[]> xmasRange;

    public Part(String line) {
        String[] cats = line.substring(1, line.length() - 1).split(",");
        xmas = new HashMap<>();
        for (String c : cats) {
            String[] l = c.split("=");
            xmas.put(l[0], Integer.parseInt(l[1]));
        }
    }

    public Part(int[] x, int[] m, int[] a, int[] s) {
        xmasRange = new HashMap<>();
        xmasRange.put("x", x);
        xmasRange.put("m", m);
        xmasRange.put("a", a);
        xmasRange.put("s", s);
    }

    public static Part copyPart(Part original, String xmas, int[] newRange) {
        Part newPart = new Part(
            Arrays.copyOf(original.xmasRange.get("x"), 2),
            Arrays.copyOf(original.xmasRange.get("m"), 2),
            Arrays.copyOf(original.xmasRange.get("a"), 2),
            Arrays.copyOf(original.xmasRange.get("s"), 2));
        newPart.xmasRange.put(xmas, newRange);
        return newPart;
    }

    public int getSumOfXmas() {
        int sum = 0;
        for (int n : xmas.values()) {
            sum += n;
        }
        return sum;
    }

    public long getProductOfPossibleXmas() {
        long product = 1;
        for (int[] r : xmasRange.values()) {
            product *= r[1] - r[0] + 1;
        }
        return product;
    }
}
