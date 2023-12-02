package se.trawe.aoc.days;

import se.trawe.aoc.Task;

import java.util.List;

public class Day24 extends Task {

    private final static Day24 instance;

    static {
        instance = new Day24();
    }

    private Day24() {
    }

    @SuppressWarnings(value = "unused")
    public static Day24 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day24().run();
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
