package se.trawe.aoc.days;

import se.trawe.aoc.util.ArrayUtil;

import java.util.List;

public class Day21 extends Task {

    private final static Day21 instance;

    static {
        instance = new Day21();
    }

    private Day21() {
    }

    @SuppressWarnings(value = "unused")
    public static Day21 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day21().run();
    }

    @Override
    protected String runTaskOne(List<String> input) {
        char[][] screen = ArrayUtil.convertListOfStringsToCharArray(input);
        int[] start = new int[2];
        boolean startFound = false;
        for (int y = 0; y < screen.length; y++) {
            for (int x = 0; x < screen[y].length; x++) {
                if (screen[y][x] == 'S') {
                    start[0] = x;
                    start[1] = y;
                    startFound = true;
                    break;
                }
            }
            if (startFound) {
                break;
            }
        }
        ArrayUtil.floodFillLimit(screen, 'O', start[0], start[1], 64);
        var count = 0;
        for (int i = 0; i < screen.length; i++) {
            char[] row = screen[i];
            for (int j = 0; j < row.length; j++) {
                char c = row[j];
                if (c == 'O' && ArrayUtil.calcManhattanDistance(start[0], start[1], j, i) % 2 == 0) {
                    count++;
                }
            }
        }
        return String.valueOf(count);
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        char[][] screen = ArrayUtil.convertListOfStringsToCharArray(input);
        int[] start = new int[2];
        boolean startFound = false;
        for (int y = 0; y < screen.length; y++) {
            for (int x = 0; x < screen[y].length; x++) {
                if (screen[y][x] == 'S') {
                    start[0] = x;
                    start[1] = y;
                    startFound = true;
                    break;
                }
            }
            if (startFound) {
                break;
            }
        }
        ArrayUtil.floodFillLimit(screen, 'O', start[0], start[1], 65);
        long evenCorners = 0L;
        long oddCorners = 0L;
        for (int i = 0; i < screen.length; i++) {
            char[] row = screen[i];
            for (int j = 0; j < row.length; j++) {
                char c = row[j];
                if (c == 'O' && ArrayUtil.calcManhattanDistance(start[0], start[1], j, i) % 2 == 0) {
                    evenCorners++;
                } else if (c == 'O') {
                    oddCorners++;
                }
            }
        }
        screen = ArrayUtil.convertListOfStringsToCharArray(input);
        ArrayUtil.floodFill(screen, 'O', start[0], start[1]);
        long evenFull = 0L;
        long oddFull = 0L;
        for (int i = 0; i < screen.length; i++) {
            char[] row = screen[i];
            for (int j = 0; j < row.length; j++) {
                char c = row[j];
                if (c == 'O' && ArrayUtil.calcManhattanDistance(start[0], start[1], j, i) % 2 == 0) {
                    evenFull++;
                } else if (c == 'O') {
                    oddFull++;
                }
            }
        }
        ArrayUtil.printCharArray(screen);

        long n = 26501365L / 131L;
        long what = (n + 1) * (n + 1) * oddFull + n * n * evenFull - (n + 1) * oddCorners + n * evenCorners;
        assert (what == 584211423220706L);
        return String.valueOf(((n+1)*(n*1)) * oddFull + (n*n) * evenFull - (n+1) * oddCorners + n * evenCorners);
    }


}
