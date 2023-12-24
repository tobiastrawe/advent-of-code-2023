package se.trawe.aoc.util;

import java.util.*;

public class ArrayUtil {

    public enum Direction {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        public final Point deltaPoint;

        Direction(int dx, int dy) {
            this.deltaPoint = new Point(dx, dy);
        }

        public Direction getOppositeDirection() {
            return switch (this) {
                case UP -> DOWN;
                case DOWN -> UP;
                case LEFT -> RIGHT;
                case RIGHT -> LEFT;
            };
        }
    }

    public record Point(int x, int y) {
    }

    public static int calcManhattanDistance(int x1, int y1, int x2, int y2) {
        return (x2 >= x1 ? x2 - x1 : x1 - x2) + (y2 >= y1 ? y2 - y1 : y1 - y2);
    }

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

    public static char[][] copy(char[][] src) {
        char[][] dst = new char[src.length][];
        for (int i = 0; i < src.length; i++) {
            dst[i] = Arrays.copyOf(src[i], src[i].length);
        }
        return dst;
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
            if (screen[x][y] == '#' || screen[x][y] == newChar)
                continue;
            screen[x][y] = newChar;
            stack.push(new int[]{x + 1, y});
            stack.push(new int[]{x - 1, y});
            stack.push(new int[]{x, y + 1});
            stack.push(new int[]{x, y - 1});
        }
    }

    public static void floodFillLimit(char[][] screen, char newChar, int startX, int startY, int limit) {
        PriorityQueue<int[]> stack = new PriorityQueue<>(Comparator.comparingInt(n -> n[2])) {
        };
        stack.add(new int[]{startX, startY, 0});
        HashMap<Point, Integer> visited = new HashMap<>();
        while (!stack.isEmpty()) {
            int[] element = stack.poll();
            var x = element[0];
            var y = element[1];
            var steps = element[2];
            if (steps > limit)
                continue;
            if (visited.containsKey(new Point(x, y)) && visited.get(new Point(x, y)) <= steps) {
                continue;
            }
            if (x < 0 || x >= screen.length || y < 0 || y >= screen[0].length)
                continue;
            if ((screen[x][y] == '#'))
                continue;
            screen[x][y] = newChar;
            visited.put(new Point(x, y), steps);
            if (steps < limit) {
                Point up = new Point(x, y -1);
                Point down = new Point(x, y +1);
                Point left = new Point(x - 1, y);
                Point right = new Point(x + 1, y);
                checkAndAddDirection(visited, up, steps, stack);
                checkAndAddDirection(visited, down, steps, stack);
                checkAndAddDirection(visited, left, steps, stack);
                checkAndAddDirection(visited, right, steps, stack);
            }
        }
    }

    private static void checkAndAddDirection(HashMap<Point, Integer> visited, Point direction, int steps, PriorityQueue<int[]> stack) {
        if (visited.containsKey(direction) && visited.get(direction) < steps + 1) {
            return;
        }
        stack.add(new int[]{direction.x, direction.y, steps + 1});
    }

    public static void printCharArray(char[][] screen) {
        for (char[] row : screen) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static void printFlippedCharArray(char[][] screen) {
        List<char[]> list = new ArrayList<>(Arrays.stream(screen).toList());
        Collections.reverse(list);
        list.forEach(row -> {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        });
    }

    public static char[][] convertListOfStringsToCharArray(List<String> input) {
        char[][] screen = new char[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            String row = input.get(y);
            for (int x = 0; x < row.length(); x++) {
                screen[y][x] = row.charAt(x);
            }
        }
        return screen;
    }

    public static int[][] convertListOfStringsToIntArray(List<String> input) {
        int[][] screen = new int[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            String row = input.get(y);
            for (int x = 0; x < row.length(); x++) {
                screen[y][x] = Character.getNumericValue(row.charAt(x));
            }
        }
        return screen;
    }
}
