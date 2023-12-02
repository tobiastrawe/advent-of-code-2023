package se.trawe.aoc.days;

import se.trawe.aoc.Task;

import java.util.List;

public class Day19 extends Task {

    private final static Day19 instance;

    static {
        instance = new Day19();
    }

    private Day19() {
    }

    @SuppressWarnings(value = "unused")
    public static Day19 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day19().run();
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
