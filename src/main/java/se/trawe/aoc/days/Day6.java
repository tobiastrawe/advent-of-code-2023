package se.trawe.aoc.days;

import se.trawe.aoc.Task;

import java.util.List;

public class Day6 extends Task {

    private final static Day6 instance;

    static {
        instance = new Day6();
    }

    private Day6() {
    }

    @SuppressWarnings(value = "unused")
    public static Day6 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day6().run();
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
