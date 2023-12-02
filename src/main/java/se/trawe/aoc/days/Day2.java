package se.trawe.aoc.days;

import se.trawe.aoc.Task;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day2 extends Task {

    private final static Day2 instance;

    static {
        instance = new Day2();
    }

    private Day2() {
    }

    @SuppressWarnings(value = "unused")
    public static Day2 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day2().run();
    }

    @Override
    public String runTaskOne(List<String> input) {
        for(String game : input) {
            String[] splitGame = game.split(":");
            String gameId = splitGame[0].split(" ")[1];
            String[] gameResults = splitGame[1].split(";");
            int i = 0;
        }
        return "no result";
    }

    @Override
    public String runTaskTwo(List<String> input) {
        return "no result";
    }

    private static HashMap<Integer, HashMap<String, Integer>> gamesMap = new HashMap<>();
}
