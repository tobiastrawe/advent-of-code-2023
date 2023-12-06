package se.trawe.aoc.days;

import java.util.List;

public class Day23 extends Task {

    private final static Day23 instance;

    static {
        instance = new Day23();
    }

    private Day23() {
    }

    @SuppressWarnings(value = "unused")
    public static Day23 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day23().run();
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
