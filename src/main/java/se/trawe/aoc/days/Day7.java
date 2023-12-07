package se.trawe.aoc.days;

import java.util.*;

public class Day7 extends Task {

    private final static Day7 instance;

    static {
        instance = new Day7();
    }

    private Day7() {
    }

    @SuppressWarnings(value = "unused")
    public static Day7 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day7().run();
    }

    private enum CamelCard {
        A(14), K(13), Q(12), J(11), T(10), NINE(9), EIGHT(8), SEVEN(7), SIX(6),
        FIVE(5), FOUR(4), THREE(3), TWO(2);


        private int cardValue;

        CamelCard(int value) {
            this.cardValue = value;
        }

        int getCardValue(boolean jokerRules) {
            if (jokerRules && this.equals(J)) {
                return 1;
            }
            return cardValue;
        }

        static CamelCard getCard(String value) {
            for (CamelCard c : CamelCard.values()) {
                try {
                    int testInt = Integer.parseInt(value);
                    if (testInt == c.getCardValue(false)) {
                        return c;
                    }
                } catch (NumberFormatException e) {
                    if (value.equals(c.name())) {
                        return c;
                    }
                }
            }
            return null;
        }
    }

    enum TypeOfHand {
        FIVE_OF_A_KIND(7),
        FOUR_OF_A_KUND(6),
        FULL_HOUSE(5),
        THREE_OF_A_KIND(4),
        TWO_PAIR(3),
        ONE_PAIR(2),
        HIGH_CARD(1);

        private int value;

        TypeOfHand(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }
    }

    record Hand(List<CamelCard> hand, long bid, TypeOfHand typeOfHand) {
    }

    class HandComparator implements Comparator<Hand> {
        private final boolean jokerRules;

        HandComparator() {
            jokerRules = false;
        }

        HandComparator(boolean jokerRules) {
            this.jokerRules = jokerRules;
        }

        @Override
        public int compare(Hand o1, Hand o2) {
            if (o1.typeOfHand.getValue() > o2.typeOfHand.getValue()) {
                return 1;
            } else if (o2.typeOfHand.getValue() > o1.typeOfHand.getValue()) {
                return -1;
            } else {
                for (int i = 0; i < 5; i++) {
                    int compareCards = Integer.compare(o1.hand.get(i).getCardValue(jokerRules), o2.hand.get(i).getCardValue(jokerRules));
                    if (compareCards != 0) {
                        return compareCards;
                    }
                }
            }
            return 0;
        }
    }

    private TypeOfHand getTypeOfHand(List<CamelCard> cards) {
        HashMap<String, Integer> numberOfCards = new HashMap<>();
        for (CamelCard c : CamelCard.values()) {
            numberOfCards.put(c.name(), 0);
        }
        for (CamelCard c : cards) {
            int n = numberOfCards.get(c.name());
            numberOfCards.put(c.name(), n + 1);
        }
        List<Integer> sorterAmountOfCards = numberOfCards.values().stream().filter(n -> n > 0).sorted().toList();
        int reducedAmount = sorterAmountOfCards.stream().reduce(1, (x, y) -> x * y);
        return switch (reducedAmount) {
            case 6 -> TypeOfHand.FULL_HOUSE;
            case 5 -> TypeOfHand.FIVE_OF_A_KIND;
            case 4 -> {
                if (sorterAmountOfCards.stream().anyMatch(i -> i == 4)) {
                    yield TypeOfHand.FOUR_OF_A_KUND;
                } else {
                    yield TypeOfHand.TWO_PAIR;
                }
            }
            case 3 -> TypeOfHand.THREE_OF_A_KIND;
            case 2 -> TypeOfHand.ONE_PAIR;
            case 1 -> TypeOfHand.HIGH_CARD;
            default -> throw new IllegalStateException("Unexpected value: " + reducedAmount);
        };
    }

    private TypeOfHand getTypeOfHandWithJokerRules(List<CamelCard> cards) {
        List<TypeOfHand> possibleTypesOfHand = new ArrayList<>();
        if (cards.contains(CamelCard.J)) {
            for (CamelCard c : CamelCard.values()) {
                if (c.equals(CamelCard.J)) {
                    continue;
                }
                List<CamelCard> copy = new ArrayList<>(cards);
                Collections.replaceAll(copy, CamelCard.J, c);
                possibleTypesOfHand.add(getTypeOfHand(copy));
            }
        } else {
            return getTypeOfHand(cards);
        }
        return possibleTypesOfHand.stream().max(new Comparator<TypeOfHand>() {
            @Override
            public int compare(TypeOfHand o1, TypeOfHand o2) {
                return Integer.compare(o1.value, o2.value);
            }
        }).orElse(TypeOfHand.HIGH_CARD);
    }

    private List<CamelCard> getCards(String s) {
        List<CamelCard> cards = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            cards.add(CamelCard.getCard(String.valueOf(s.charAt(i))));
        }
        return cards;
    }

    @Override
    protected String runTaskOne(List<String> input) {
        List<Hand> hands = new ArrayList<>();
        for (String line : input) {
            String[] split = line.split(" ");
            List<CamelCard> cards = getCards(split[0]);
            hands.add(new Hand(cards, Integer.parseInt(split[1]), getTypeOfHand(cards)));
        }
        hands.sort(new HandComparator());
        int rank = 1;
        long totalValue = 0;
        for (Hand h : hands) {
            totalValue += h.bid * rank;
            rank++;
        }
        return String.valueOf(totalValue);
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        List<Hand> hands = new ArrayList<>();
        for (String line : input) {
            String[] split = line.split(" ");
            List<CamelCard> cards = getCards(split[0]);
            TypeOfHand typeOfHand = getTypeOfHandWithJokerRules(cards);
            hands.add(new Hand(cards, Integer.parseInt(split[1]), typeOfHand));
        }
        hands.sort(new HandComparator(true));
        int rank = 1;
        long totalValue = 0;
        for (Hand h : hands) {
            totalValue += h.bid * rank;
            rank++;
        }
        return String.valueOf(totalValue);
    }
}
