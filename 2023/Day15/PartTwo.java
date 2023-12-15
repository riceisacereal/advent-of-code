import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PartTwo {
    static class Lens {
        private Lens prev;
        private Lens next;
        private String label;
        private String focalLength;
        private int hash;

        public Lens(String line) {
            String[] step = line.split("=");
            this.label = step[0];
            this.focalLength = step[1];
            this.hash = calculateHash(this.label);
        }

        public Lens() {
            // create dummy lens
            this.label = "_";
        }

        public static int calculateHash(String label) {
            int hash = 0;
            for (char c : label.toCharArray()) {
                hash = ((hash + c) * 17) % 256;
            }
            return hash;
        }

        public Lens getPrev() {
            return prev;
        }

        public Lens getNext() {
            return next;
        }

        public int getHash() {
            return hash;
        }

        public void setPrev(Lens prev) {
            this.prev = prev;
        }

        public void setNext(Lens next) {
            this.next = next;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int remove(String labelToRemove) {
            if (this.label.equals(labelToRemove)) {
                this.prev.setNext(this.next);
                if (this.next != null) this.next.setPrev(this.prev);
                return 1; // Successfully removed
            } else if (this.next == null) {
                return 2; // Not found
            } else {
                return this.next.remove(labelToRemove);
            }
        }

        public int add(Lens l) {
            if (this.label.equals(l.label)) {
                this.focalLength = l.focalLength;
                return 1; // Replaced label
            } else if (this.next == null) {
                this.next = l;
                l.setPrev(this);
                return 2; // Added at the end
            } else {
                return this.next.add(l);
            }
        }
    }

    private static final String puzzleInput = "2023/Day15/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int parseInput(List<String> lines) {
        Lens[] boxes = new Lens[256];
        for (int i = 0; i < 256; i++) {
            boxes[i] = new Lens();
        }

        for (String line : lines.get(0).split(",")) {
            if (line.charAt(line.length() - 1) == '-') {
                // Remove
                String label = line.substring(0, line.length() - 1);
                int index = Lens.calculateHash(label);
                if (boxes[index] != null) boxes[index].remove(label);
            } else {
                // Replace or add
                Lens l = new Lens(line);
                int index = l.getHash();
                if (boxes[index] == null) boxes[index] = l;
                else boxes[index].add(l);
            }
        }
        int points = 0;
        for (int i = 0; i < 256; i++) {
            Lens l = boxes[i].getNext();
            int slotNum = 1;
            while (l != null) {
                points += (i + 1) * slotNum * Integer.parseInt(l.focalLength);
                slotNum++;
                l = l.getNext();
            }
        }
        return points;
    }
}
