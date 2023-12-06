package se.trawe.aoc.days;

import java.util.List;

public class Day22 extends Task {

    private final static Day22 instance;

    static {
        instance = new Day22();
    }

    private Day22() {
    }

    @SuppressWarnings(value = "unused")
    public static Day22 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day22().run();
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
