package se.trawe.aoc.days;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Day1 extends Task {

    private final static Day1 instance;

    private static final Map<String, String> index = new HashMap<>();

    static {
        instance = new Day1();
        index.put("zero", "0");
        index.put("one", "1");
        index.put("two", "2");
        index.put("three", "3");
        index.put("four", "4");
        index.put("five", "5");
        index.put("six", "6");
        index.put("seven", "7");
        index.put("eight", "8");
        index.put("nine", "9");
    }

    private Day1() {
    }

    @SuppressWarnings(value = "unused")
    public static Day1 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day1().run();
    }

    @Override
    protected String runTaskOne(List<String> input) {
        AtomicInteger result = new AtomicInteger();
        input.forEach(s -> {
            String numbers = s.replaceAll("\\D", "");
            String concat = "" + numbers.charAt(0) +
                    numbers.charAt(numbers.length() - 1);
            result.addAndGet(Integer.parseInt(concat));
        });
        return result.toString();
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        int result = 0;
        for (String s : input) {
            StringBuilder combinedNumbers = new StringBuilder();
            for (int i = 0; i <= s.length(); i++) {
                int c = Character.getNumericValue(s.charAt(i));
                if (c <= 9 && c >= 0) {
                    combinedNumbers.append(c);
                    break;
                }
                String findNumber = findNumberFromString(s.substring(i));
                if (findNumber != null) {
                    combinedNumbers.append(findNumber);
                    break;
                }
            }
            for (int i = s.length() - 1; i >= 0; i--) {
                int c = Character.getNumericValue(s.charAt(i));
                if (c <= 9 && c >= 0) {
                    combinedNumbers.append(c);
                    break;
                }
                String findNumber = findNumberFromString(s.substring(i));
                if (findNumber != null) {
                    combinedNumbers.append(findNumber);
                    break;
                }
            }
            result += Integer.parseInt(combinedNumbers.toString());
        }
        return String.valueOf(result);
    }

    private String findNumberFromString(String s) {
        for (String numberAsString : index.keySet()) {
            if (s.startsWith(numberAsString)) {
                return index.get(numberAsString);
            }
        }
        return null;
    }
}
