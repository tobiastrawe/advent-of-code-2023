package se.trawe.aoc.days;

import java.util.List;

public class Day11 extends Task {

    private final static Day11 instance;

    static {
        instance = new Day11();
    }

    private Day11() {
    }

    @SuppressWarnings(value = "unused")
    public static Day11 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day11().run();
    }

    @Override
    protected String runTaskOne(List<String> input) {
        return "no result";
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        return "no result";
    }


}
