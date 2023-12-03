package se.trawe.aoc.days;

import se.trawe.aoc.Task;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 extends Task {

    private final static Day3 instance;

    static {
        instance = new Day3();
    }

    private Day3() {
    }

    @SuppressWarnings(value = "unused")
    public static Day3 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day3().run();
    }

    public record Coordinate(int x, int y) {}

    @Override
    public String runTaskOne(List<String> input) {
        Set<Coordinate> coordinates = generateCoordinates(input, "[\\D]");
        int sum = 0;
        for (String line : input) {
            Matcher matcher = Pattern.compile("\\d+").matcher(line);
            while (matcher.find()) {
                String numbers = matcher.group();
                int row = input.indexOf(line);
                if (coordinates.stream().anyMatch(c -> checkCoordinates(c, matcher.start(), matcher.end(), row))) {
                    sum += Integer.parseInt(numbers);
                }
            }
        }
        return String.valueOf(sum);
    }

    private Set<Coordinate> generateCoordinates(List<String> input, String pattern) {
        Set<Coordinate> coordinates = new HashSet<>();
        int row = 0;
        for (String line : input) {
            for (int i = 0; i < line.length() - 1; i++) {
                String character = line.substring(i, i+1);
                if (character.matches(pattern)) {
                    if (!character.equals(".")) {
                        coordinates.add(new Coordinate(i, row));
                    }
                }
            }
            row += 1;
        }
        return coordinates;
    }

    public boolean checkCoordinates(Coordinate c, int x1, int x2, int y) {
        for (int x = x1; x < x2; x++) {
            if (x >= c.x-1 && x <= c.x+1 && y >= c.y-1 && y <= c.y+1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String runTaskTwo(List<String> input) {
        Set<Coordinate> coordinates = generateCoordinates(input, "\\*");
        Map<Coordinate, String> numberCoordinates = new HashMap<>();
        int sum = 0;
        int row = 0;
        for (String line : input) {
            Matcher matcher = Pattern.compile("\\d+").matcher(line);
            while (matcher.find()) {
                numberCoordinates.put(new Coordinate(matcher.start(), row), matcher.group());
            }
            row += 1;
        }
        for (Coordinate coord : coordinates) {
            List<String> numbersAdjacentToCoord = new ArrayList<>(List.of());
            numberCoordinates.keySet().forEach(c -> {
                int size = numberCoordinates.get(c).length();
                if (checkCoordinates(coord, c.x, c.x + size, c.y)) {
                    numbersAdjacentToCoord.add(numberCoordinates.get(c));
                }
            });
            if (numbersAdjacentToCoord.size() == 2) {
                sum += Integer.parseInt(numbersAdjacentToCoord.get(0)) * Integer.parseInt(numbersAdjacentToCoord.get(1));
            }
        }
        return String.valueOf(sum);
    }


}
