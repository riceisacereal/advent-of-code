import java.util.Arrays;

public class Shared {
    public static int[][] getIntArray(String line) {
        String[] card = line.split(": ")[1].split(" \\| ");
        int[] winNums = Arrays.stream(card[0].trim().split(" +"))
            .mapToInt(Integer::parseInt)
            .toArray();
        int[] ownNums = Arrays.stream(card[1].trim().split(" +"))
            .mapToInt(Integer::parseInt)
            .toArray();

        return new int[][] {winNums, ownNums};
    }
}
