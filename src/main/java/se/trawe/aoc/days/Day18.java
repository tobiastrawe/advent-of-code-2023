package se.trawe.aoc.days;

import se.trawe.aoc.Task;

import java.util.List;

public class Day18 extends Task {

    private final static Day18 instance;

    static {
        instance = new Day18();
    }

    private Day18() {
    }

    @SuppressWarnings(value = "unused")
    public static Day18 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day18().run();
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
