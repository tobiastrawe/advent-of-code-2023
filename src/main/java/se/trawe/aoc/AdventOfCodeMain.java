package se.trawe.aoc;

import se.trawe.aoc.days.Task;

public class AdventOfCodeMain {

    public static void main(String[] args) {
        for (int i = 1; i <= 25; i++) {
            try {
                Task.register(Task.getTask(i));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Task.runAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
