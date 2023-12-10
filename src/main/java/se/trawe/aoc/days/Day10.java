package se.trawe.aoc.days;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        private final int x, y;

        Cardinal(int x, int y) {
            this.x = x;
            this.y = y;
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

    protected record direction (Cardinal x, Cardinal y) {}

    private static HashMap<Character, direction> directionMapper = new HashMap<>();

    static {
        directionMapper.put('|', new direction(Cardinal.NORTH, Cardinal.SOUTH));
        directionMapper.put('-', new direction(Cardinal.EAST, Cardinal.WEST));
        directionMapper.put('L', new direction(Cardinal.NORTH, Cardinal.EAST));
        directionMapper.put('J', new direction(Cardinal.NORTH, Cardinal.WEST));
        directionMapper.put('7', new direction(Cardinal.SOUTH, Cardinal.WEST));
        directionMapper.put('F', new direction(Cardinal.SOUTH, Cardinal.EAST));

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
        for (Cardinal cardinal : Cardinal.values()) {
            try {
                if (doesSquareContainCardinal(pipeGrid[currentY + cardinal.y][currentX + cardinal.x], cardinal)) {
                    cardinalToIgnore = getOppositeCardinal(cardinal);
                    currentX += cardinal.x;
                    currentY += cardinal.y;
                    break;
                }
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
        while (true) {
            char current = pipeGrid[currentY][currentX];
            direction dir = directionMapper.get(current);
            Cardinal directionOne = dir.x;
            Cardinal directionTwo = dir.y;
            if (!directionOne.equals(cardinalToIgnore)) {
                currentX += directionOne.x;
                currentY += directionOne.y;
            } else if (!directionTwo.equals(cardinalToIgnore)) {
                currentX += directionTwo.x;
                currentY += directionTwo.y;
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
        direction d = directionMapper.get(c);
        return d.x.equals(cardinal) || d.y.equals(cardinal);
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        return "no result";
    }


}
