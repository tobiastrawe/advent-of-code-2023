package se.trawe.aoc.days;

import se.trawe.aoc.Task;

import java.util.List;

public class Day3 extends Task {

    private final static Day3 instance;

    static {
        instance = new Day3();
    }

    private Day3() {
    }

    @SuppressWarnings(value = "unused")
    public static Day3 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day3().run();
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
