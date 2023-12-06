package se.trawe.aoc.days;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6 extends Task {

    private final static Day6 instance;

    static {
        instance = new Day6();
    }

    private Day6() {
    }

    @SuppressWarnings(value = "unused")
    public static Day6 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day6().run();
    }

    protected record Race(long time, long record) {}

    @Override
    protected String runTaskOne(List<String> input) {
        Set<Race> races = new HashSet<>();
        List<Long> times = new ArrayList<>();
        List<Long> distances = new ArrayList<>();
        for (String s : input) {
            if (s.startsWith("Time:")) {
                Matcher m = Pattern.compile("\\d+").matcher(s);
                while (m.find()) {
                    times.add(Long.parseLong(m.group()));
                }
            } else if (s.startsWith("Distance:")) {
                Matcher m = Pattern.compile("\\d+").matcher(s);
                while (m.find()) {
                    distances.add(Long.parseLong(m.group()));
                }
            }
        }
        for(int i = 0; i < times.size(); i++) {
            races.add(new Race(times.get(i), distances.get(i)));
        }
        List<Long> numberOfWaysToWin = races.stream().map(r -> {
            long firstWinAt = 0;
            for (long i = 1; i < r.time; i++) {
                if (i * (r.time - i) > r.record) {
                    firstWinAt = i;
                    break;
                }
            }
            return (r.time - (firstWinAt * 2)) + 1;
        }).toList();
        return numberOfWaysToWin.stream().reduce(Long.parseLong("1"), (x, y) -> x * y).toString();
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        List<String> newInput = List.of("Time: " + input.get(0).replaceAll("\\D", ""),
                "Distance: " + input.get(1).replaceAll("\\D", ""));
        return runTaskOne(newInput);
    }
}
