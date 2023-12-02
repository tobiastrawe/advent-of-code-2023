package se.trawe.aoc.days;

import se.trawe.aoc.Task;

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
    public String runTaskOne(List<String> input) {
        return "no result";
    }

    @Override
    public String runTaskTwo(List<String> input) {
        return "no result";
    }
}
