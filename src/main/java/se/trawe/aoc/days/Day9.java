package se.trawe.aoc.days;

import se.trawe.aoc.Result;
import se.trawe.aoc.Task;

public class Day9 extends Task {

    private final static Day9 instance;

    static {
        instance = new Day9();
    }

    @SuppressWarnings(value = "unused")
    public static Day9 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day9().run();
    }

    private Day9() {}

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
