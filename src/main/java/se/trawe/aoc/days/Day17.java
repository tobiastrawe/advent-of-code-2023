package se.trawe.aoc.days;

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
    protected String runTaskOne(List<String> input) {
        return "no result";
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        return "no result";
    }


}
