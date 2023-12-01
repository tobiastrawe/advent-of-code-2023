package se.trawe.aoc.days;

import se.trawe.aoc.Result;
import se.trawe.aoc.Task;

public class Day19 extends Task {

    private final static Day19 instance;

    static {
        instance = new Day19();
    }

    @SuppressWarnings(value = "unused")
    public static Day19 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day19().run();
    }

    private Day19() {}

    //TODO: code task one
    public String runTestOne() {
        return "no result";
    }

    //TODO: code task two
    public String runTestTwo() {
        return "no result";
    }

    @Override
    protected void run() {
        Result result = new Result(this.getClass().getSimpleName(), runTestOne(), runTestTwo());
        System.out.println(result);
    }
}
