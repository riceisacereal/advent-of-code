import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class CamelCard implements Comparable<CamelCard> {
    public String cardStrength = "AKQT98765432J";
    private String hand;
    private int bid;
    private int rank;

    public CamelCard(String line) {
        String[] parts = line.trim().split(" ");
        this.hand = parts[0];
        this.bid = Integer.parseInt(parts[1]);

        HashMap<Character, Integer> cards = new HashMap<>();
        for (int i = 0; i < this.hand.length(); i++) {
            char c = this.hand.charAt(i);
            if (cards.get(c) == null) {
                cards.put(c, 1);
            } else {
                cards.put(c, cards.get(c) + 1);
            }
        }

        /*
        7- 5 of a kind 1 index
        6- 4 of a kind 2 index 4 : 1
        5- full house  2 index 3 : 2
        4- 3 of a kind 3 index 3 : 1 : 1
        3- 2 pair      3 index 2 : 2 : 1
        2- 1 pair      4 index
        1- high card   5 index
        */

        switch (cards.size()) {
            case 1 -> this.rank = 7;
            case 2 -> {
                this.rank = 5;
                for (int count : cards.values()) {
                    if (count >= 4) {
                        this.rank = 6;
                        break;
                    }
                }
                if (cards.get('J') != null) {
                    this.rank= 7;
                }
            }
            case 3 -> {
                this.rank = 3;
                if (cards.get('J') != null) {
                    if (cards.get('J') == 1) {
                        this.rank = 5;
                    } else if (cards.get('J') == 2) {
                        this.rank = 6;
                    }
                }
                for (int count : cards.values()) {
                    if (count >= 3) {
                        this.rank = 4;
                        if (cards.get('J') != null) {
                            this.rank = 6;
                        }
                        break;
                    }
                }
            }
            case 4 -> {
                this.rank = 2;
                if (cards.get('J') != null) {
                    this.rank = 4;
                }
            }
            case 5 -> {
                this.rank = 1;
                if (cards.get('J') != null) {
                    this.rank = 2;
                }
            }
        }
    }

    public int getBid() {
        return this.bid;
    }

    public int compareTo(CamelCard other) {
        if (this.rank != other.rank) {
            return -(this.rank - other.rank);
        }

        for (int i = 0; i < this.hand.length(); i++) {
            char thisC = this.hand.charAt(i);
            char otherC = other.hand.charAt(i);
            if (thisC != otherC) {
                return cardStrength.indexOf(thisC) - cardStrength.indexOf(otherC);
            }
        }

        return 0;
    }
}

public class PartOne {
    private static final String puzzleInput = "2023/Day07/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = readFile(puzzleInput);
        int result = parseInput(lines);
        System.out.println(result);
    }

    public static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public static int parseInput(List<String> lines) {
        int points = 0;
        ArrayList<CamelCard> allCards = new ArrayList<>();
        for (String line : lines) {
            allCards.add(new CamelCard(line));
        }
        allCards.sort(CamelCard::compareTo);
        int size = allCards.size();
        for (int i = size; i >= 1; i--) {
            System.out.println(i + " " + allCards.get(size - i).getBid());
            points += i * allCards.get(size - i).getBid();
        }

        return points;
    }
}
