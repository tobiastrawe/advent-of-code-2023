package se.trawe.aoc.days;

import se.trawe.aoc.Result;
import se.trawe.aoc.Task;

public class Day4 extends Task {

    private final static Day4 instance;

    static {
        instance = new Day4();
    }

    @SuppressWarnings(value = "unused")
    public static Day4 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day4().run();
    }

    private Day4() {}

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
