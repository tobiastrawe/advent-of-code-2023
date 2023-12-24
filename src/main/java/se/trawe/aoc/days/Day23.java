package se.trawe.aoc.days;

import se.trawe.aoc.util.ArrayUtil;
import se.trawe.aoc.util.ArrayUtil.Point;
import se.trawe.aoc.util.ArrayUtil.Direction;

import java.util.*;

public class Day23 extends Task {

    private final static Day23 instance;

    static {
        instance = new Day23();
    }

    private Day23() {
        slopeMap.put(Direction.UP, '^');
        slopeMap.put(Direction.DOWN, 'v');
        slopeMap.put(Direction.LEFT, '<');
        slopeMap.put(Direction.RIGHT, '>');
    }

    @SuppressWarnings(value = "unused")
    public static Day23 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day23().run();
    }

    private class Node {
        private final Point point;
        private HashMap<Direction, Integer> stepsToNextNode;
        private Node nextNode;

        public Node(Point point) {
            this.point = point;
        }
    }

    HashMap<Direction, Character> slopeMap = new HashMap<>();
    Set<Integer> finalPathSteps = new HashSet<>();
    int highestCount = 0;

    private void longestPathfinder(char[][] map, int x, int y, Direction comingFrom, Set<Point> visited, Point finish, int steps) {
        List<Direction> possibleDirections;
        while (true) {
            int finalY = y;
            int finalX = x;
            Direction finalComingFrom = comingFrom;
            possibleDirections = Arrays.stream(Direction.values()).filter(d -> !d.equals(finalComingFrom)
                    && !visited.contains(new Point(finalX + d.deltaPoint.x(), finalY + d.deltaPoint.y()))
                    && (map[finalY + d.deltaPoint.y()][finalX + d.deltaPoint.x()] == '.'
                    || (map[finalY + d.deltaPoint.y()][finalX + d.deltaPoint.x()] == slopeMap.get(d)))).toList();
            if (possibleDirections.size() > 1) {
                // check if node already exists, in that case use the existing nodes to keep tracing path
                // otherwise create new node and split into as many directions as exists
                for (Direction dir : possibleDirections) {
                    longestPathfinder(map, x + dir.deltaPoint.x(), y + dir.deltaPoint.y(),
                            dir.getOppositeDirection(), new HashSet<>(visited), finish, steps+1);
                }
                break;
            } else if (possibleDirections.isEmpty()) {
                break;
            } else {
                steps++;
                Direction dir = possibleDirections.get(0);
                comingFrom = dir.getOppositeDirection();
                x += dir.deltaPoint.x();
                y += dir.deltaPoint.y();
                visited.add(new Point(x, y));
                if (finish.x() == x && finish.y() == y) {
                    finalPathSteps.add(steps);
                    if (steps > highestCount) {
                        highestCount = steps;
                        System.out.println(highestCount);
                    }
                    break;
                }
            }
        }
    }

    @Override
    protected String runTaskOne(List<String> input) {
        char[][] map = ArrayUtil.convertListOfStringsToCharArray(input);
        Node startPosition = null;
        Node finalPosition = null;
        char[] chars = map[0];
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '.') {
                startPosition = new Node(new Point(i, 0));
            }
        }
        chars = map[map.length-1];
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '.') {
                finalPosition = new Node(new Point(i, map.length - 1));
            }
        }
        assert startPosition != null;
        longestPathfinder(map, startPosition.point.x(), startPosition.point.y(), Direction.UP, new HashSet<>(), new Point(finalPosition.point.x(), finalPosition.point.y()), 0);
        return String.valueOf(finalPathSteps.stream().reduce(0, (x, y) -> x > y ? x : y));
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        char[][] map = ArrayUtil.convertListOfStringsToCharArray(input);
        Node startPosition = null;
        Node finalPosition = null;
        for (int i = 0; i < map.length; i++) {
            char[] row = map[i];
            for (int j = 0; j < row.length; j++) {
                char c = row[j];
                if (c != '.' && c != '#') {
                    map[i][j] = '.';
                }
            }
        }
        char[] chars = map[0];
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '.') {
                startPosition = new Node(new Point(i, 0));
            }
        }
        chars = map[map.length-1];
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '.') {
                finalPosition = new Node(new Point(i, map.length - 1));
            }
        }
        assert startPosition != null;
        longestPathfinder(map, startPosition.point.x(), startPosition.point.y(), Direction.UP, new HashSet<>(), new Point(finalPosition.point.x(), finalPosition.point.y()), 0);
        return String.valueOf(finalPathSteps.stream().reduce(0, (x, y) -> x > y ? x : y));
    }


}
