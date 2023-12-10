package se.trawe.aoc.util;

import se.trawe.aoc.days.Day10;

import java.util.Stack;

public class ArrayUtil {
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
}
