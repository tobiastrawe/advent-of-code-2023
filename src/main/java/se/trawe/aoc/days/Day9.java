package se.trawe.aoc.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 extends Task {

    private final static Day9 instance;

    static {
        instance = new Day9();
    }

    private Day9() {
    }

    @SuppressWarnings(value = "unused")
    public static Day9 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day9().run();
    }

    @Override
    protected String runTaskOne(List<String> input) {
        int sum = input.stream().mapToInt(line ->
                getNextInSequence(Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray())
        ).sum();
        return String.valueOf(sum);
    }

    private int getNextInSequence(int[] numbers) {
        var next = numbers[numbers.length-1];
        var last = next;
        int[] differences;
        while (Arrays.stream(numbers).allMatch(i -> i == 0)) {
            differences = new int[numbers.length - 1];
            for (int i = 0; i < numbers.length - 1; i++) {
                differences[i] = numbers[i+1] - numbers[i];
            }
            last = differences[differences.length-1];
            next += last;
            numbers = differences;
        }

        return next;
    }

    private int getFirstInSequence(int[] numbers) {
        var first = 0;
        int[] differences;
        ArrayList<int[]> allDifferences = new ArrayList<>();
        allDifferences.add(Arrays.stream(numbers).toArray());
        while (true) {
            differences = new int[numbers.length - 1];
            for (int i = 0; i < numbers.length - 1; i++) {
                differences[i] = numbers[i+1] - numbers[i];
            }
            if (Arrays.stream(differences).allMatch(i -> i == 0)) {
                break;
            }
            allDifferences.add(differences);
            numbers = differences;
        }
        for(int i = allDifferences.size() - 1; i >= 0; i--) {
            int[] workingArray = allDifferences.get(i);
            first = workingArray[0] - first;
        }
        return first;
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        int sum = input.stream().mapToInt(line ->
                getFirstInSequence(Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray())
        ).sum();
        return String.valueOf(sum);
    }


}
