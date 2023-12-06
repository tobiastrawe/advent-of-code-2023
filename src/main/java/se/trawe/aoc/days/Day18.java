package se.trawe.aoc.days;

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
    protected String runTaskOne(List<String> input) {
        return "no result";
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        return "no result";
    }


}
