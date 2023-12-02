package se.trawe.aoc.days;

import se.trawe.aoc.Task;

import java.util.List;

public class Day5 extends Task {

    private final static Day5 instance;

    static {
        instance = new Day5();
    }

    private Day5() {
    }

    @SuppressWarnings(value = "unused")
    public static Day5 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day5().run();
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
