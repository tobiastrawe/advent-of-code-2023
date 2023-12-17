package se.trawe.aoc.days;

import se.trawe.aoc.util.ArrayUtil;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Day16 extends Task {

    private final static Day16 instance;

    static {
        instance = new Day16();
    }

    private Day16() {
    }

    @SuppressWarnings(value = "unused")
    public static Day16 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day16().run();
    }

    private enum Direction {
        NORTH(0, -1),
        SOUTH(0, 1),
        EAST(1, 0),
        WEST(-1, 0);

        private final int dx;
        private final int dy;

        private Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    private class Beam {
        private Point position;
        private Direction direction;

        public Beam(Point position, Direction direction) {
            this.position = position;
            this.direction = direction;
        }
    }

    @Override
    protected String runTaskOne(List<String> input) {
        char[][] screen = ArrayUtil.convertListOfStringsToCharArray(input);
        ArrayList<Beam> beams = new ArrayList<>();
        beams.add(new Beam(new Point(-1, 0), Direction.EAST));
        Set<Point> pointsCoveredByLaser = new HashSet<>();
        Set<Point> splitterStop = new HashSet<>();
        while (!beams.isEmpty()) {
            ArrayList<Beam> beamsToRemove = new ArrayList<>();
            ArrayList<Beam> beamsToAdd = new ArrayList<>();
            for (Beam b : beams) {
                pointsCoveredByLaser.add(new Point(b.position.x, b.position.y));
                Point nextPosition = new Point(b.position.x + b.direction.dx, b.position.y + b.direction.dy);
                char c;
                try {
                    c = screen[nextPosition.y][nextPosition.x];
                } catch (IndexOutOfBoundsException ignored) {
                    beamsToRemove.add(b);
                    continue;
                }
                if ((c == '-' && (b.direction.equals(Direction.NORTH) || b.direction.equals(Direction.SOUTH)))
                        || (c == '|' && (b.direction.equals(Direction.EAST) || b.direction.equals(Direction.WEST)))) {
                    if (!splitterStop.contains(nextPosition)) {
                        splitterStop.add(nextPosition);
                        beamsToAdd.addAll(splitBeam(nextPosition, c));
                    }
                    beamsToRemove.add(b);
                } else if (c == '\\' || c == '/') {
                    b.direction = changeBeamDirection(b.direction, c);
                }
                b.position = nextPosition;
            }
            beams.removeAll(beamsToRemove);
            beams.addAll(beamsToAdd);
        }
        for (Point p :pointsCoveredByLaser) {
            if (p.x < 0) {
                continue;
            }
            screen[p.y][p.x] = '#';
        }
        int counter = 0;

        for (int y = 0; y < screen.length; y++) {
            var row = screen[y];
            for (int x = 0; x < row.length; x++) {
                if (row[x] == '#') {
                    counter++;
                }
                System.out.print(row[x]);
            }
            System.out.println();
        }

        return String.valueOf(pointsCoveredByLaser.size() - 1);
    }

    private List<Beam> splitBeam(Point startingPoint, char splitChar) {
        Beam beamOne = new Beam(startingPoint, splitChar == '|' ? Direction.NORTH : Direction.EAST);
        Beam beamTwo = new Beam(startingPoint, splitChar == '|' ? Direction.SOUTH : Direction.WEST);
        return List.of(beamOne, beamTwo);
    }

    private Direction changeBeamDirection(Direction currentDirection, char mirrorChar) {
        if (currentDirection.equals(Direction.NORTH)) {
            return mirrorChar == '/' ? Direction.EAST : Direction.WEST;
        } else if (currentDirection.equals(Direction.SOUTH)) {
            return mirrorChar == '/' ? Direction.WEST : Direction.EAST;
        } else if (currentDirection.equals(Direction.EAST)) {
            return mirrorChar == '/' ? Direction.NORTH : Direction.SOUTH;
        } else if (currentDirection.equals(Direction.WEST)) {
            return mirrorChar == '/' ? Direction.SOUTH : Direction.NORTH;
        }
        return null;
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        return "no result";
    }


}
