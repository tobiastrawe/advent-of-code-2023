package se.trawe.aoc.days;

import java.util.List;

public class Day13 extends Task {

    private final static Day13 instance;

    static {
        instance = new Day13();
    }

    private Day13() {
    }

    @SuppressWarnings(value = "unused")
    public static Day13 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day13().run();
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
