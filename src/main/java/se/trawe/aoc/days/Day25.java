package se.trawe.aoc.days;

import java.util.List;

public class Day25 extends Task {

    private final static Day25 instance;

    static {
        instance = new Day25();
    }

    private Day25() {
    }

    @SuppressWarnings(value = "unused")
    public static Day25 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day25().run();
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
