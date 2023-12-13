package se.trawe.aoc.util;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.Set;

public class ArrayUtil {

    public static Set<Point> getCoordinatesForCharacter(char[][] screen, char c) {
        Set<Point> coordinates = new HashSet<>();
        for (int y = 0; y < screen.length; y++) {
            var row = screen[y];
            for (int x = 0; x < row.length; x++) {
                if (row[x] == c) {
                    coordinates.add(new Point(x, y));
                }
            }
        }
        return coordinates;
    }

    public static char[][] rotateCW(char[][] mat) {
        final int M = mat.length;
        final int N = mat[0].length;
        char[][] ret = new char[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M - 1 - r] = mat[r][c];
            }
        }
        return ret;
    }

    public static void floodFill(char[][] screen, char newChar, int startX, int startY) {
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startX, startY});

        while (!stack.isEmpty()) {
            int[] coordinates = stack.pop();
            var x = coordinates[0];
            var y = coordinates[1];
            if (x < 0 || x >= screen.length || y < 0 || y >= screen[0].length)
                continue;
            if (screen[x][y] != '.' && screen[x][y] != '#')
                continue;
            screen[x][y] = newChar;
            stack.push(new int[]{x + 1, y});
            stack.push(new int[]{x - 1, y});
            stack.push(new int[]{x, y + 1});
            stack.push(new int[]{x, y - 1});
        }
    }

    public static char[][] convertListOfStringsToCharArray(List<String> input) {
        char[][] universe = new char[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            String row = input.get(y);
            for (int x = 0; x < row.length(); x++) {
                universe[y][x] = row.charAt(x);
            }
        }
        return universe;
    }
}
