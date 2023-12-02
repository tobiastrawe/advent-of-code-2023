package se.trawe.aoc.days;

import se.trawe.aoc.Task;

import java.util.List;

public class Day17 extends Task {

    private final static Day17 instance;

    static {
        instance = new Day17();
    }

    private Day17() {
    }

    @SuppressWarnings(value = "unused")
    public static Day17 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day17().run();
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
