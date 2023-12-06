package se.trawe.aoc.days;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 extends Task {

    private final static Day2 instance;

    static {
        instance = new Day2();
    }

    private Day2() {
    }

    @SuppressWarnings(value = "unused")
    public static Day2 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day2().run();
    }

    private static final Pattern colorPattern = Pattern.compile("\\s*(\\d+)\\s*(red|blue|green).*");

    private static final String red = "red";
    private static final String green = "green";
    private static final String blue = "blue";

    private static final int maxRed = 12;
    private static final int maxGreen = 13;
    private static final int maxBlue = 14;

    @Override
    protected String runTaskOne(List<String> input) {
        int sumOfValidGames = 0;
        for(String game : input) {
            String[] splitGame = game.split(":");
            int gameId = Integer.parseInt(splitGame[0].split(" ")[1]);
            if (isGameValid(splitGame[1].split(";"))) {
                sumOfValidGames += gameId;
            }
        }
        return String.valueOf(sumOfValidGames);
    }

    private boolean isGameValid(String[] gameResults) {
        for (String result : gameResults) {
            for (String colorResult : result.split(",")) {
                Matcher m = colorPattern.matcher(colorResult);
                if (m.matches()) {
                    String color = m.group(2);
                    int amount = Integer.parseInt(m.group(1));
                    switch (color) {
                        case red -> {
                            if (amount > maxRed) {
                                return false;
                            }
                        }
                        case green -> {
                            if (amount > maxGreen) {
                                return false;
                            }
                        }
                        case blue -> {
                            if (amount > maxBlue) {
                                return false;
                            }
                        }
                    }
                } else {
                    throw new RuntimeException("WTF?");
                }
            }
        }
        return true;
    }

    private int powerOfGame(String[] gameResults) {
        int highRed = 0, highBlue = 0, highGreen = 0;
        for (String result : gameResults) {
            for (String colorResult : result.split(",")) {
                Matcher m = colorPattern.matcher(colorResult);
                if (m.matches()) {
                    String color = m.group(2);
                    int amount = Integer.parseInt(m.group(1));
                    switch (color) {
                        case red -> {
                            if (highRed < amount) {
                                highRed = amount;
                            }
                        }
                        case green -> {
                            if (highGreen < amount) {
                                highGreen = amount;
                            }
                        }
                        case blue -> {
                            if (highBlue < amount) {
                                highBlue = amount;
                            }
                        }
                    }
                } else {
                    throw new RuntimeException("WTF?");
                }
            }
        }
        return highRed * highGreen * highBlue;
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        int sumOfValidGames = 0;
        for(String game : input) {
            String[] splitGame = game.split(":");
            sumOfValidGames += powerOfGame(splitGame[1].split(";"));
        }
        return String.valueOf(sumOfValidGames);
    }
}
