package se.trawe.aoc.days;

import se.trawe.aoc.Task;

import java.util.List;

public class Day8 extends Task {

    private final static Day8 instance;

    static {
        instance = new Day8();
    }

    private Day8() {
    }

    @SuppressWarnings(value = "unused")
    public static Day8 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day8().run();
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
