package se.trawe.aoc.days;

import java.util.List;

public class Day12 extends Task {

    private final static Day12 instance;

    static {
        instance = new Day12();
    }

    private Day12() {
    }

    @SuppressWarnings(value = "unused")
    public static Day12 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day12().run();
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
