package se.trawe.aoc.days;

import se.trawe.aoc.Task;

import java.util.List;

public class Day4 extends Task {

    private final static Day4 instance;

    static {
        instance = new Day4();
    }

    private Day4() {
    }

    @SuppressWarnings(value = "unused")
    public static Day4 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day4().run();
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
