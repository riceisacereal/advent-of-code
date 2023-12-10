import static java.util.Map.entry;
import java.util.Map;

class Pipe {
    //     N 0
    // W 3     E 1
    //     S 2
    //
    // The magic number is the sum of the directions which are connected
    // This way we can get the other direction by subtracting the incoming side from the magic number
    public static Map<Character, Integer> pipeToMagicNum = Map.ofEntries(
        entry('|', 2),
        entry('-', 4),
        entry('L', 1),
        entry('F', 3),
        entry('7', 5),
        entry('J', 3)
    );
    // Only needed one time to start the loop
    public static Map<Character, Integer> randomStart = Map.ofEntries(
        entry('|', 0), // 0 or 2
        entry('-', 1), // 1 or 3
        entry('L', 0), // 0 or 1
        entry('F', 1), // 1 or 2
        entry('7', 2), // 2 or 3
        entry('J', 0)  // 0 or 3
    );
    private final char pipe;
    private final int pipeMagicNum;
    private Boolean partOfLoop = false;

    public Pipe(char p) {
        this.pipe = p;
        this.pipeMagicNum = pipeToMagicNum.get(p);
    }

    public int getRandomStart() {
        return randomStart.get(this.pipe);
    }

    public void setPartOfLoop() {
        this.partOfLoop = true;
    }

    public Boolean isPartOfLoop() {
        return partOfLoop;
    }

    public char getPipe() {
        return pipe;
    }

    public int getNextDirection(int incoming) {
        return pipeMagicNum - incoming;
    }
}