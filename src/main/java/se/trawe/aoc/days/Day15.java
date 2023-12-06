package se.trawe.aoc.days;

import java.util.List;

public class Day15 extends Task {

    private final static Day15 instance;

    static {
        instance = new Day15();
    }

    private Day15() {
    }

    @SuppressWarnings(value = "unused")
    public static Day15 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day15().run();
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
