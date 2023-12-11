package se.trawe.aoc.days;

import se.trawe.aoc.util.ArrayUtil;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.List;

public class Day11 extends Task {

    private final static Day11 instance;

    static {
        instance = new Day11();
    }

    private Day11() {
    }

    @SuppressWarnings(value = "unused")
    public static Day11 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day11().run();
    }

    @Override
    protected String runTaskOne(List<String> input) {
        char[][] universe = getUniverseScreen(input);
        List<Integer> expandX = generateIndices(universe);
        List<Integer> expandY = generateIndices(ArrayUtil.rotateCW(universe));
        Set<Point> coordinates = ArrayUtil.getCoordinatesForCharacter(universe, '#');
        var result = 0L;
        for (Point p : coordinates) {
            result += coordinates.stream().filter(c -> !c.equals(p)).mapToLong(c -> manhattanDistanceUniverse(p, c, expandY, expandX, 1)).sum();
        }
        return String.valueOf(result/2);
    }

    private static List<Integer> generateIndices(char[][] universe) {
        List<Integer> expandX = new ArrayList<>();
        for (int y = 0; y < universe.length; y++) {
            var row = universe[y];
            if (!Arrays.toString(row).contains("#")) {
                expandX.add(y);
            }
        }
        return expandX;
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        char[][] universe = getUniverseScreen(input);
        List<Integer> expandX = generateIndices(universe);
        List<Integer> expandY = generateIndices(ArrayUtil.rotateCW(universe));
        Set<Point> coordinates = ArrayUtil.getCoordinatesForCharacter(universe, '#');
        var result = 0L;
        for (Point p : coordinates) {
            result += coordinates.stream().filter(c -> !c.equals(p)).mapToLong(c ->
                    manhattanDistanceUniverse(p, c, expandY, expandX, 1000000-1)).sum();
        }
        return String.valueOf(result/2);
    }

    private static char[][] getUniverseScreen(List<String> input) {
        char[][] universe = new char[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            String row = input.get(y);
            for (int x = 0; x < row.length(); x++) {
                universe[y][x] = row.charAt(x);
            }
        }
        return universe;
    }

    private long manhattanDistanceUniverse(Point p, Point c, List<Integer> expandY, List<Integer> expandX, long extraSteps) {
        return (Math.abs(p.x - c.x) + (numbersIntersecting(p.x, c.x, expandY) * extraSteps)) + (Math.abs(p.y - c.y) + (numbersIntersecting(p.y, c.y, expandX) * extraSteps));
    }

    private int numbersIntersecting(int n1, int n2, List<Integer> numbers) {
        var result = 0;
        var from = Math.min(n1, n2);
        var to = Math.max(n1, n2);
        for (; from < to; from++) {
            if (numbers.contains(from)) {
                result++;
            }
        }
        return result;
    }
}
