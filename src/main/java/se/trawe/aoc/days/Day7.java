package se.trawe.aoc.days;

import se.trawe.aoc.Task;

import java.util.List;

public class Day7 extends Task {

    private final static Day7 instance;

    static {
        instance = new Day7();
    }

    private Day7() {
    }

    @SuppressWarnings(value = "unused")
    public static Day7 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day7().run();
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
