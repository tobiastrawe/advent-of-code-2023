package se.trawe.aoc.days;

import java.util.List;

public class Day10 extends Task {

    private final static Day10 instance;

    static {
        instance = new Day10();
    }

    private Day10() {
    }

    @SuppressWarnings(value = "unused")
    public static Day10 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day10().run();
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
