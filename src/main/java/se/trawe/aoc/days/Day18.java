package se.trawe.aoc.days;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18 extends Task {

    private final static Day18 instance;

    static {
        instance = new Day18();
    }

    private Day18() {
    }

    @SuppressWarnings(value = "unused")

    public static Day18 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day18().run();
    }

    private static synchronized void loadData(List<String> input) {
        if (!firstOrderList.isEmpty()) {
            return;
        }
        Pattern p = Pattern.compile("(\\w+)\\s(\\d+)\\s\\(#(.*)\\)");
        for (String line : input) {
            Matcher m = p.matcher(line);
            if (m.matches()) {
                Direction direction = null;
                switch (m.group(1)) {
                    case "U" -> direction = Direction.UP;
                    case "D" -> direction = Direction.DOWN;
                    case "R" -> direction = Direction.RIGHT;
                    case "L" -> direction = Direction.LEFT;
                }
                int longDigLength = Integer.parseInt(m.group(3).substring(0, 5), 16);
                int directionCode = Integer.parseInt(m.group(3).substring(5, 6));
                Direction longDigDirection = null;
                switch (directionCode) {
                    case 3 -> longDigDirection = Direction.UP;
                    case 1 -> longDigDirection = Direction.DOWN;
                    case 0 -> longDigDirection = Direction.RIGHT;
                    case 2 -> longDigDirection = Direction.LEFT;
                }
                DigOrder firstOrder = new DigOrder(direction, Integer.parseInt(m.group(2)));
                DigOrder secondOrder = new DigOrder(longDigDirection, longDigLength);
                firstOrderList.add(firstOrder);
                secondOrderList.add(secondOrder);
            }
        }
    }

    private enum Direction {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        private final Point deltaPoint;

        Direction(int dx, int dy) {
            this.deltaPoint = new Point(dx, dy);
        }
    }

    private record DigOrder(Direction direction, int digLength) {
    }

    private static final List<DigOrder> firstOrderList = new ArrayList<>();
    private static final List<DigOrder> secondOrderList = new ArrayList<>();

    private record Point(int x, int y) {
    }

    @Override
    protected String runTaskOne(List<String> input) {
        loadData(input);
        ArrayList<Point> digPattern = new ArrayList<>();
        Point startPosition = new Point(0, 0);
        digPattern.add(startPosition);
        Point currentPoint = startPosition;
        long perimeter = 1;
        for (DigOrder order : firstOrderList) {
            int x = currentPoint.x + (order.direction.deltaPoint.x * order.digLength);
            int y = currentPoint.y + (order.direction.deltaPoint.y * order.digLength);
            Point digPoint = new Point(x, y);
            currentPoint = digPoint;
            digPattern.add(digPoint);
            perimeter += order.digLength();
        }
        long area = shoeLace(digPattern);
        return String.valueOf((perimeter / 2L) + area + 1);
    }

    private static void drawDigPatternDebug(char[][] screen) {
        for (char[] row : screen) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        loadData(input);
        ArrayList<Point> digPattern = new ArrayList<>();
        Point startPosition = new Point(0, 0);
        digPattern.add(startPosition);
        Point currentPoint = startPosition;
        long perimeter = 1;
        for (DigOrder order : secondOrderList) {
            int x = currentPoint.x + (order.direction.deltaPoint.x * order.digLength);
            int y = currentPoint.y + (order.direction().deltaPoint.y * order.digLength);
            Point digPoint = new Point(x, y);
            currentPoint = digPoint;
            digPattern.add(digPoint);
            perimeter += order.digLength;
        }
        long area = shoeLace(digPattern);
        return String.valueOf((perimeter / 2L) + area + 1);
    }

    private long shoeLace(ArrayList<Point> points) {
        int n = points.size();
        long area = 0L;

        // Calculate the sum of (x[i] * y[i + 1] - y[i] * x[i + 1])
        for (int i = 0; i < n - 1; i++) {
            Point current = points.get(i);
            Point next = points.get(i + 1);
            area += ((long) current.x * next.y) - ((long) current.y * next.x);
        }

        // Add the last term (x[n - 1] * y[0] - y[n - 1] * x[0])
        Point current = points.get(points.size() - 2);
        Point next = points.get(points.size() - 1);
        area += ((long) current.x * next.y) - ((long) current.y * next.x);

        // Take absolute value and half
        area = (Math.abs(area) / 2L);

        return area;
    }
}
