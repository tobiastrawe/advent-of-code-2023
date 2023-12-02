package se.trawe.aoc.days;

import se.trawe.aoc.Task;

import java.util.List;

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

    @Override
    public String runTaskOne(List<String> input) {
        return "no result";
    }

    @Override
    public String runTaskTwo(List<String> input) {
        return "no result";
    }
}
