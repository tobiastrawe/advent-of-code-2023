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

    private record BeamKey(Point position, Direction direction) {
    }

    @Override
    protected String runTaskOne(List<String> input) {
        char[][] screen = ArrayUtil.convertListOfStringsToCharArray(input);
        Set<Point> beamPositions = calculateBeamPositions(new Beam(new Point(0, 0), Direction.EAST), screen);
        int positionsCovered = beamPositions.size();
        //debugFinalPositions(beamPositions, screen);
        return String.valueOf(positionsCovered);
    }

    private void debugFinalPositions(Set<Point> beamPoints, char[][] screen) {
        for (Point pos : beamPoints) {
            if (screen[pos.y][pos.x] == '.') {
                screen[pos.y][pos.x] = '#';
            }
        }
        for (char[] row : screen) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private final Map<BeamKey, Set<Point>> cachedBeamPositions = new HashMap<>();

    private Set<Point> calculateBeamPositions(Beam beam, char[][] screen) {
        Set<Point> beamPositions = new HashSet<>();
        BeamKey beamKey = new BeamKey(new Point(beam.position.x, beam.position.y), beam.direction);
        if (cachedBeamPositions.containsKey(beamKey)) {
            return cachedBeamPositions.get(beamKey);
        }
        cachedBeamPositions.put(beamKey, beamPositions);
        while (true) {
            char c;
            try {
                c = screen[beam.position.y][beam.position.x];
            } catch (IndexOutOfBoundsException ignored) {
                break;
            }
            if ((c == '-' && (beam.direction.equals(Direction.NORTH) || beam.direction.equals(Direction.SOUTH)))
                    || (c == '|' && (beam.direction.equals(Direction.EAST) || beam.direction.equals(Direction.WEST)))) {
                for (Beam sBeam : splitBeam(beam.position, c)) {
                    beamPositions.addAll(calculateBeamPositions(sBeam, screen));
                }
                break;
            } else if (c == '\\' || c == '/') {
                beam.direction = changeBeamDirection(beam.direction, c);
            }
            beamPositions.add(new Point(beam.position.x, beam.position.y));
            beam.position = new Point(beam.position.x + beam.direction.dx, beam.position.y + beam.direction.dy);;
        }
        return beamPositions;
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
        char[][] screen = ArrayUtil.convertListOfStringsToCharArray(input);
        int maxPositionsCovered = 0;
        for (int i = 0; i < screen.length; i++) {
            Beam beamEast = new Beam(new Point(0, i), Direction.EAST);
            Beam beamWest = new Beam(new Point(screen[i].length - 1, i), Direction.WEST);
            int positionsCovered = Math.max(calculateBeamPositions(beamEast, screen).size(), calculateBeamPositions(beamWest, screen).size());
            maxPositionsCovered = Math.max(maxPositionsCovered, positionsCovered);
        }
        for (int i = 0; i < screen[0].length; i++) {
            Beam beamWest = new Beam(new Point(i, 0), Direction.SOUTH);
            Beam beamEast = new Beam(new Point(i, screen.length - 1), Direction.NORTH);
            int positionsCovered = Math.max(calculateBeamPositions(beamEast, screen).size(), calculateBeamPositions(beamWest, screen).size());
            maxPositionsCovered = Math.max(maxPositionsCovered, positionsCovered);
        }
        //debugFinalPositions(beamPositions, screen);
        return String.valueOf(maxPositionsCovered);
    }
}
