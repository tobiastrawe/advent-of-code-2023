package se.trawe.aoc.days;

import se.trawe.aoc.Task;

import java.util.List;

public class Day21 extends Task {

    private final static Day21 instance;

    static {
        instance = new Day21();
    }

    private Day21() {
    }

    @SuppressWarnings(value = "unused")
    public static Day21 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day21().run();
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
