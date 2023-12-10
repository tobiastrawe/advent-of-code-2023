package se.trawe.aoc.days;

import java.util.HashMap;
import java.util.List;

public class Day10 extends Task {

    private final static Day10 instance;

    static {
        instance = new Day10();
    }

    private Day10() {
    }

    @SuppressWarnings(value = "unused")
    public static Day10 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day10().run();
    }
    
    private enum Cardinal {
        NORTH(0, -1),
        EAST(1, 0),
        SOUTH(0, 1),
        WEST(-1, 0);

        private final int deltaX, deltaY;

        Cardinal(int deltaX, int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }
    }

    protected record Coordinate (int x, int y) {
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Coordinate c) {
                return this.x == c.x && this.y == c.y;
            }
            return false;
        }
    }

    protected record Direction(Cardinal directionOne, Cardinal directionTwo) {}

    private static HashMap<Character, Direction> directionMapper = new HashMap<>();

    static {
        directionMapper.put('|', new Direction(Cardinal.NORTH, Cardinal.SOUTH));
        directionMapper.put('-', new Direction(Cardinal.EAST, Cardinal.WEST));
        directionMapper.put('L', new Direction(Cardinal.NORTH, Cardinal.EAST));
        directionMapper.put('J', new Direction(Cardinal.NORTH, Cardinal.WEST));
        directionMapper.put('7', new Direction(Cardinal.SOUTH, Cardinal.WEST));
        directionMapper.put('F', new Direction(Cardinal.SOUTH, Cardinal.EAST));

    }

    @Override
    protected String runTaskOne(List<String> input) {
        char[][] pipeGrid = new char[input.size()][input.get(0).length()];
        var startX = 0;
        var startY = 0;
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (c == 'S') {
                    startX = j;
                    startY = i;
                }
                pipeGrid[i][j] = line.charAt(j);
            }
        }
        var steps = 0;
        var currentX = startX;
        var currentY = startY;
        Cardinal cardinalToIgnore = null;
        // determine starting direction
        for (Cardinal cardinal : Cardinal.values()) {
            try {
                if (doesSquareContainCardinal(pipeGrid[currentY + cardinal.deltaY][currentX + cardinal.deltaX], getOppositeCardinal(cardinal))) {
                    cardinalToIgnore = getOppositeCardinal(cardinal);
                    currentX += cardinal.deltaX;
                    currentY += cardinal.deltaY;
                    steps += 1;
                    break;
                }
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
        while (true) {
            char current = pipeGrid[currentY][currentX];
            Direction dir = directionMapper.get(current);
            if (!dir.directionOne.equals(cardinalToIgnore)) {
                cardinalToIgnore = getOppositeCardinal(dir.directionOne);
                currentX += dir.directionOne.deltaX;
                currentY += dir.directionOne.deltaY;
            } else if (!dir.directionTwo.equals(cardinalToIgnore)) {
                cardinalToIgnore = getOppositeCardinal(dir.directionTwo);
                currentX += dir.directionTwo.deltaX;
                currentY += dir.directionTwo.deltaY;
            } else {
                throw new RuntimeException("what?");
            }

            steps++;
            if (currentX == startX && currentY == startY) {
                break;
            }
        }
        return String.valueOf(steps / 2);
    }

    private Cardinal getOppositeCardinal(Cardinal cardinal) {
        if (cardinal.equals(Cardinal.WEST)) {
            return Cardinal.EAST;
        } else if (cardinal.equals(Cardinal.EAST)) {
            return Cardinal.WEST;
        } else if (cardinal.equals(Cardinal.NORTH)) {
            return Cardinal.SOUTH;
        }
        return Cardinal.NORTH;
    }

    private boolean doesSquareContainCardinal(char c, Cardinal cardinal) {
        if (c == '.') {
            return false;
        } else if (c == 'S') {
            return true;
        }
        Direction d = directionMapper.get(c);
        return d.directionOne.equals(cardinal) || d.directionTwo.equals(cardinal);
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        return "no result";
    }


}
