package se.trawe.aoc.days;

import se.trawe.aoc.util.ArrayUtil;

import java.util.*;

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

    protected record Coordinate(int x, int y) {
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Coordinate c) {
                return this.x == c.x && this.y == c.y;
            }
            return false;
        }
    }

    protected record Direction(Cardinal directionOne, Cardinal directionTwo) {
    }

    private static final HashMap<Character, Direction> directionMapper = new HashMap<>();

    static {
        directionMapper.put('|', new Direction(Cardinal.NORTH, Cardinal.SOUTH));
        directionMapper.put('-', new Direction(Cardinal.EAST, Cardinal.WEST));
        directionMapper.put('L', new Direction(Cardinal.NORTH, Cardinal.EAST));
        directionMapper.put('J', new Direction(Cardinal.NORTH, Cardinal.WEST));
        directionMapper.put('7', new Direction(Cardinal.SOUTH, Cardinal.WEST));
        directionMapper.put('F', new Direction(Cardinal.SOUTH, Cardinal.EAST));
    }

    private static int startX = 0;
    private static int startY = 0;

    private static char[][] generatePipeGrid(List<String> input) {
        char[][] pipeGrid = new char[input.size()][input.get(0).length()];
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
        return pipeGrid;
    }

    private static Set<Coordinate> createPipeLine(char[][] pipeGrid) {
        Set<Coordinate> pipeCoordinates = new HashSet<>();
        var currentX = startX;
        var currentY = startY;
        pipeCoordinates.add(new Coordinate(startX, startY));
        Cardinal cardinalToIgnore = null;
        // determine starting direction
        for (Cardinal cardinal : Cardinal.values()) {
            try {
                if (doesCharacterContainCardinal(pipeGrid[currentY + cardinal.deltaY][currentX + cardinal.deltaX], getOppositeCardinal(cardinal))) {
                    cardinalToIgnore = getOppositeCardinal(cardinal);
                    currentX += cardinal.deltaX;
                    currentY += cardinal.deltaY;
                    pipeCoordinates.add(new Coordinate(currentX, currentY));
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
            pipeCoordinates.add(new Coordinate(currentX, currentY));
            if (currentX == startX && currentY == startY) {
                break;
            }
        }
        return pipeCoordinates;
    }

    @Override
    protected String runTaskOne(List<String> input) {
        char[][] pipeGrid = generatePipeGrid(input);
        Set<Coordinate> coordinatesPassed = createPipeLine(pipeGrid);
        return String.valueOf(coordinatesPassed.size() / 2);
    }

    private static Cardinal getOppositeCardinal(Cardinal cardinal) {
        if (cardinal.equals(Cardinal.WEST)) {
            return Cardinal.EAST;
        } else if (cardinal.equals(Cardinal.EAST)) {
            return Cardinal.WEST;
        } else if (cardinal.equals(Cardinal.NORTH)) {
            return Cardinal.SOUTH;
        }
        return Cardinal.NORTH;
    }

    private static boolean doesCharacterContainCardinal(char c, Cardinal cardinal) {
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
        char[][] pipeGrid = generatePipeGrid(input);
        Set<Coordinate> pipeLine = createPipeLine(pipeGrid);
        var bigPipeGrid = tripleSizeOfGrid(pipeGrid, pipeLine);
        ArrayUtil.floodFill(bigPipeGrid, ' ', 0, 0);
        var counter = 0;
        for (char[] row : bigPipeGrid) {
            for (char c : row) {
                if (c == '#')
                    counter += 1;
            }
        }
        /*try {
            FileUtil.WriteToFile("./test.txt", bigPipeGrid);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }*/
        return String.valueOf(counter);
    }

    private static char[][] tripleSizeOfGrid(char[][] pipeGrid, Set<Coordinate> pipeLine) {
        char[][] bigPipeGrid = new char[pipeGrid.length * 3][pipeGrid[0].length * 3];
        for (int i = 0; i < pipeGrid.length; i++) {
            var row = pipeGrid[i];
            for (int j = 0; j < row.length; j++) {
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        if (x == 1 && y == 1) {
                            bigPipeGrid[(i * 3) + x][(j * 3) + y] = '#';
                        } else {
                            bigPipeGrid[(i * 3) + x][(j * 3) + y] = '.';
                        }
                    }
                }
                Coordinate testCoordinate = new Coordinate(j, i);
                if (pipeLine.remove(testCoordinate)) {
                    Direction d = directionMapper.get(row[j] == 'S' ? getStartingPositionCharacter(pipeGrid, j, i) : row[j]);
                    bigPipeGrid[(i * 3) + 1][(j * 3) + 1] = '*';
                    bigPipeGrid[(i * 3) + 1 + d.directionOne.deltaY][(j * 3) + 1 + d.directionOne.deltaX] = '*';
                    bigPipeGrid[(i * 3) + 1 + d.directionTwo.deltaY][(j * 3) + 1 + d.directionTwo.deltaX] = '*';
                }
            }
        }
        return bigPipeGrid;
    }

    private static char getStartingPositionCharacter(char[][] screen, int x, int y) {
        System.out.println(screen[y][x]);
        boolean north = doesCharacterContainCardinal(screen[y-1][x], Cardinal.SOUTH);
        boolean south = doesCharacterContainCardinal(screen[y+1][x], Cardinal.NORTH);
        boolean east = doesCharacterContainCardinal(screen[y][x+1], Cardinal.WEST);
        boolean west = doesCharacterContainCardinal(screen[y][x-1], Cardinal.EAST);

        if (north && south) {
            return '|';
        } else if (north && east) {
            return 'L';
        } else if (north && west) {
            return 'J';
        } else if (south && east) {
            return 'F';
        } else if (south && west) {
            return '7';
        } else if (east && west) {
            return '-';
        }
        return 'X';
    }
}
