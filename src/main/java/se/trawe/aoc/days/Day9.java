package se.trawe.aoc.days;

import se.trawe.aoc.Task;

import java.util.List;

public class Day9 extends Task {

    private final static Day9 instance;

    static {
        instance = new Day9();
    }

    private Day9() {
    }

    @SuppressWarnings(value = "unused")
    public static Day9 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day9().run();
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
