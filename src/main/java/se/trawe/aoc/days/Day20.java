package se.trawe.aoc.days;

import java.util.List;

public class Day20 extends Task {

    private final static Day20 instance;

    static {
        instance = new Day20();
    }

    private Day20() {
    }

    @SuppressWarnings(value = "unused")
    public static Day20 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day20().run();
    }

    private enum ModuleType {
        BUTTON,
        BROADCASTER,
        FLIP_FLOP,
        CONJUNCTION;
    }

    private record Module(ModuleType type, List<String> targets) {}

    @Override
    protected String runTaskOne(List<String> input) {
        return "no result";
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        return "no result";
    }


}
