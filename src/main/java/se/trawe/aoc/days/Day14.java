package se.trawe.aoc.days;

import se.trawe.aoc.util.ArrayUtil;
import se.trawe.aoc.util.MathUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                        boulderArray[y-emptySpace][x] = 'O';
                        boulderPositions.add(new Point(x, y-emptySpace));
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
        char[][] boulderArray = ArrayUtil.convertListOfStringsToCharArray(input);
        List<Long> resultList = new ArrayList<>();
        List<Long> repeatList = new ArrayList<>();
        for (long i = 0; i < 1000000000; i++) {
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
            resultList.add(boulderPositions.stream().mapToLong(p -> finalBoulderArray.length - p.y).sum());
            if (1000000000 % (i+1) == 0) {
                System.out.println(boulderPositions.stream().mapToLong(p -> finalBoulderArray.length - p.y).sum());}
        }
        return "no result";
    }


}
