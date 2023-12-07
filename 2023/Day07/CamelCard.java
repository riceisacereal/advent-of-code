import java.util.HashMap;

public class CamelCard implements Comparable<CamelCard> {
    private final String[] cardStrength = {"AKQJT98765432", "AKQT98765432J"}; // Order for part 1 & 2
    private final int part;
    private final String hand;
    private final int bid;
    private final int rank;

    /*
        7- 5 of a kind 1 index
        6- 4 of a kind 2 index 4 : 1
        5- full house  2 index 3 : 2
        4- 3 of a kind 3 index 3 : 1 : 1
        3- 2 pair      3 index 2 : 2 : 1
        2- 1 pair      4 index
        1- high card   5 index
     */

    private int getRankPartOne(HashMap<Character, Integer> cards) {
        switch (cards.size()) {
            case 1 -> {
                return 7;
            }
            case 2 -> {
                // When there are 2 different cards, could be 4 of a kind, or full house
                for (int count : cards.values()) {
                    // If 4 of a kind
                    if (count >= 4) {
                        return 6;
                    }
                }
                // Else is full house
                return 5;
            }
            case 3 -> {
                // When there are 3 different cards, could be 3 of a kind, or 2 pair
                for (int count : cards.values()) {
                    // If 3 of a kind
                    if (count >= 3) {
                        return 4;
                    }
                }
                // else is 2 pair
                return 3;
            }
            case 4 -> {
                return 2;
            }
            case 5 -> {
                return 1;
            }
            default -> {
                System.out.println("This shouldn't happen: Part One");
                return -1;
            }
        }
    }

    private int getRankPartTwo(HashMap<Character, Integer> cards) {
        switch (cards.size()) {
            case 1 -> {
                return 7;
            }
            case 2 -> {
                // If a Joker card exists anywhere, can be combined in any way into 5 of a kind
                if (cards.get('J') != null) {
                    return 7;
                }
                for (int count : cards.values()) {
                    if (count >= 4) {
                        return 6;
                    }
                }
                return 5;
            }
            case 3 -> {
                for (int count : cards.values()) {
                    // If 3 of a kind
                    if (count >= 3) {
                        // If a Joker card exists anywhere, the best choice is always 4 of a kind,
                        // instead of full house
                        if (cards.get('J') != null) {
                            return 6;
                        }
                        return 4;
                    }
                }
                // Else this is a 2 pair
                if (cards.get('J') != null) {
                    if (cards.get('J') == 1) {
                        // If there is only one joker, full house is the only option
                        return 5;
                    } else if (cards.get('J') == 2) {
                        // If there are two jokers, 4 of a kind is the best option
                        return 6;
                    }
                }
                return 3;
            }
            case 4 -> {
                // If a Joker exists in any pile (1 or 2), the best option is to always combine
                // into a 3 of a kind
                if (cards.get('J') != null) {
                    return 4;
                }
                return 2;
            }
            case 5 -> {
                // If a Joker exists, the best option is to always combine into a 3 of a kind
                if (cards.get('J') != null) {
                    return 2;
                }
                return 1;
            }
            default -> {
                System.out.println("This shouldn't happen: Part Two");
                return -1;
            }
        }
    }

    public CamelCard(String line, int part) {
        String[] parts = line.trim().split(" ");
        this.hand = parts[0];
        this.bid = Integer.parseInt(parts[1]);
        this.part = part - 1;

        HashMap<Character, Integer> cards = new HashMap<>();
        for (int i = 0; i < this.hand.length(); i++) {
            char c = this.hand.charAt(i);
            cards.merge(c, 1, Integer::sum);
        }

        if (part == 1) {
            this.rank = getRankPartOne(cards);
        } else {
            this.rank = getRankPartTwo(cards);
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
                return cardStrength[part].indexOf(thisC) - cardStrength[part].indexOf(otherC);
            }
        }

        return 0;
    }
}