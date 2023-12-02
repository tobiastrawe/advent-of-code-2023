package se.trawe.aoc.days;

import se.trawe.aoc.Task;

import java.util.List;

public class Day14 extends Task {

    private final static Day14 instance;

    static {
        instance = new Day14();
    }

    private Day14() {
    }

    @SuppressWarnings(value = "unused")
    public static Day14 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day14().run();
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
