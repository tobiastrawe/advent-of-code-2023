package se.trawe.aoc.days;

import se.trawe.aoc.util.ArrayUtil;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Day14 extends Task {

    private final static Day14 instance;

    static {
        instance = new Day14();
    }

    private Day14() {
    }

    @SuppressWarnings(value = "unused")
    public static Day14 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day14().run();
    }

    @Override
    protected String runTaskOne(List<String> input) {
        char[][] boulderArray = ArrayUtil.convertListOfStringsToCharArray(input);
        Set<Point> boulderPositions = moveBoulders(boulderArray);
        return String.valueOf(boulderPositions.stream().mapToLong(p -> boulderArray.length - p.y).sum());
    }

    private static Set<Point> moveBoulders(char[][] boulderArray) {
        Set<Point> boulderPositions = new HashSet<>();
        for (int x = 0; x < boulderArray[0].length; x++) {
            int emptySpace = 0;
            for (int y = 0; y < boulderArray.length; y++) {
                if (boulderArray[y][x] == '#') {
                    emptySpace = 0;
                } else if (boulderArray[y][x] == 'O') {
                    if (emptySpace > 0) {
                        boulderArray[y][x] = '.';
                        boulderArray[y - emptySpace][x] = 'O';
                        boulderPositions.add(new Point(x, y - emptySpace));
                    } else {
                        boulderPositions.add(new Point(x, y));
                    }
                } else if (boulderArray[y][x] == '.') {
                    emptySpace++;
                }
            }
        }
        return boulderPositions;
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        var iterations = 1000000000;
        char[][] boulderArray = ArrayUtil.convertListOfStringsToCharArray(input);
        Map<Set<Point>, Integer> repeatMap = new HashMap<>();
        var isRepeating = false;
        List<Integer> repeatingList = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            for (int cycle = 0; cycle < 4; cycle++) {
                moveBoulders(boulderArray);
                boulderArray = ArrayUtil.rotateCW(boulderArray);
            }
            Set<Point> boulderPositions = new HashSet<>();
            for (int y = 0; y < boulderArray.length; y++) {
                for (int x = 0; x < boulderArray[y].length; x++) {
                    if (boulderArray[y][x] == 'O') {
                        boulderPositions.add(new Point(x, y));
                    }
                }
            }
            char[][] finalBoulderArray = boulderArray;
            var result = boulderPositions.stream().mapToInt(p -> finalBoulderArray.length - p.y).sum();
            if (isRepeating) {
                if (repeatingList.contains(result)) {
                    var targetIndex = (iterations - i - repeatingList.size() - 1) % repeatingList.size();
                    return String.valueOf(repeatingList.get(targetIndex));
                } else {
                    repeatingList.add(result);
                }
            } else if (repeatMap.containsKey(boulderPositions)) {
                isRepeating = true;
            } else {
                repeatMap.put(boulderPositions, i);
            }
        }
        return "no result";
    }


}
