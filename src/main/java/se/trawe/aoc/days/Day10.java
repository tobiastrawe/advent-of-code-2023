package se.trawe.aoc.days;

import se.trawe.aoc.Task;

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
    public String runTaskOne(List<String> input) {
        return "no result";
    }

    @Override
    public String runTaskTwo(List<String> input) {
        return "no result";
    }


}
