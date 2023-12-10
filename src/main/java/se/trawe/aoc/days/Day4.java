package se.trawe.aoc.days;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Day4 extends Task {

    private final static Day4 instance;

    static {
        instance = new Day4();
    }

    private Day4() {
    }

    @SuppressWarnings(value = "unused")
    public static Day4 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day4().run();
    }

    @Override
    protected String runTaskOne(List<String> input) {
        int total = getCards(input).values().stream().mapToInt(Integer::intValue).sum();
        return String.valueOf(total);
    }

    private HashMap<Integer, Integer> getCards(List<String> input) {
        Pattern isNumber = Pattern.compile("\\d+");
        HashMap<Integer, Integer> cards = new HashMap<>();
        int cardNumber = 0;
        for (String card : input) {
            int result = 0;
            String results = card.split(":")[1];
            String[] winningNumbers = results.split("\\|")[0].split(" ");
            List<String> numbersOwned = Arrays.stream(results.split("\\|")[1].split(" ")).toList();
            for (int i = 0; i < Arrays.stream(winningNumbers).filter(isNumber.asMatchPredicate()).filter(numbersOwned::contains).count(); i++) {
                result++;
            }
            cards.put(++cardNumber, result);
        }
        return cards;
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        HashMap<Integer, Integer> pointsOfCards = getCards(input);
        HashMap<Integer, Integer> numberOfCards = new HashMap<>();
        List<Integer> winningCards = new java.util.ArrayList<>(List.of());
        pointsOfCards.forEach((k, v) -> {
            if (v > 0) {
                winningCards.add(k);
            }
        });
        pointsOfCards.keySet().forEach((k) -> numberOfCards.put(k, 1));

        for (int x = 0; x < winningCards.size(); x++) {
            int number = winningCards.get(x);
            for(int i = 0; i < pointsOfCards.get(number); i++) {
                numberOfCards.put(number + i + 1, numberOfCards.get(number + i + 1) + 1);
                winningCards.add(number + i + 1);
            }
        }
        return String.valueOf(numberOfCards.values().stream().mapToInt(Integer::valueOf).sum());
    }


}
