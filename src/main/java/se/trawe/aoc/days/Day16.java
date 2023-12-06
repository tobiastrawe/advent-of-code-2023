package se.trawe.aoc.days;

import java.util.List;

public class Day16 extends Task {

    private final static Day16 instance;

    static {
        instance = new Day16();
    }

    private Day16() {
    }

    @SuppressWarnings(value = "unused")
    public static Day16 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day16().run();
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
