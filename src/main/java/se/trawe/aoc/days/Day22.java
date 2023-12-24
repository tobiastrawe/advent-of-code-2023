package se.trawe.aoc.days;

import se.trawe.aoc.util.ArrayUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Day22 extends Task {

    private final static Day22 instance;

    static {
        instance = new Day22();
    }

    private Day22() {
    }

    @SuppressWarnings(value = "unused")
    public static Day22 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day22().run();
    }

    private record Brick(int[] x, int[] y, int[] z, char name) {
    }

    private final List<Brick> brickList = new ArrayList<>();
    private final HashSet<Brick> bricksSafeToDelete = new HashSet<>();
    private final HashMap<Brick, Set<Brick>> supportedBrickMap = new HashMap<>();
    private final HashMap<Brick, Set<Brick>> supportBrickMap = new HashMap<>();
    private final HashSet<Brick> doubleCheckSafeToDelete = new HashSet<>();

    private char[][] xz;
    private char[][] yz;

    private void drawState() {
        System.out.println("xz:");
        ArrayUtil.printFlippedCharArray(xz);
        System.out.println("yz:");
        ArrayUtil.printFlippedCharArray(yz);
    }

    @Override
    protected String runTaskOne(List<String> input) {
        init(input);
        return String.valueOf(bricksSafeToDelete.size() - doubleCheckSafeToDelete.size());
    }

    boolean initialized = false;

    private synchronized void init(List<String> input) {
        if (!initialized) {
            initialized = true;
            int cn = 65;
            int counter = 0;
            for (String brick : input) {
                String[] splitBrick = brick.split("~");
                String[] left = splitBrick[0].split(",");
                String[] right = splitBrick[1].split(",");
                brickList.add(new Brick(new int[]{left[0].transform(Integer::parseInt), right[0].transform(Integer::parseInt)},
                        new int[]{left[1].transform(Integer::parseInt), right[1].transform(Integer::parseInt)},
                        new int[]{left[2].transform(Integer::parseInt), right[2].transform(Integer::parseInt)},
                        (char) (cn + counter++)));
            }
            bricksSafeToDelete.addAll(brickList);
            boolean bricksNotResting = true;
            while (bricksNotResting) {
                bricksNotResting = false;
                for (Brick b : brickList) {
                    if (b.z[0] > 1 && brickList.stream().noneMatch(brick ->
                            !brick.equals(b) && isRestingOn(brick.x, brick.y, brick.z, b.x, b.y, b.z))) {
                        b.z[0]--;
                        b.z[1]--;
                        bricksNotResting = true;
                    }
                }
            }

            for (Brick b : brickList) {
                supportedBrickMap.put(b, brickList.stream().filter(brick -> {
                            if (!brick.equals(b) && isRestingOn(brick.x, brick.y, brick.z, b.x, b.y, b.z)) {
                                bricksSafeToDelete.remove(brick);
                                return true;
                            }
                            return false;
                        }
                ).collect(Collectors.toUnmodifiableSet()));
                supportBrickMap.put(b, brickList.stream().filter(brick ->
                                !brick.equals(b) && isRestingOn(b.x, b.y, b.z, brick.x, brick.y, brick.z))
                        .collect(Collectors.toUnmodifiableSet()));
            }
            supportedBrickMap.values().stream().filter(list -> list.size() >= 2).forEach(bricksSafeToDelete::addAll);
            bricksSafeToDelete.forEach(b -> {
                if (supportedBrickMap.values().stream().filter(list -> list.size() == 1).anyMatch(brickList -> brickList.contains(b))) {
                    doubleCheckSafeToDelete.add(b);
                }
                ;
            });
        }
    }

    private void generateScreen() {
        xz = new char[500][500];
        yz = new char[500][500];
        for (char[] r : xz) {
            Arrays.fill(r, '.');
        }
        Arrays.fill(xz[0], '-');
        for (char[] r : yz) {
            Arrays.fill(r, '.');
        }
        Arrays.fill(yz[0], '-');
        for (Brick brick : brickList) {
            for (int i = brick.x[0]; i <= brick.x[1]; i++) {
                for (int j = brick.z[0]; j <= brick.z[1]; j++) {
                    if (xz[j][i] == '.') {
                        xz[j][i] = brick.name;
                    } else {
                        xz[j][i] = '?';
                    }
                }
            }
        }
        for (Brick brick : brickList) {
            for (int i = brick.y[0]; i <= brick.y[1]; i++) {
                for (int j = brick.z[0]; j <= brick.z[1]; j++) {
                    if (yz[j][i] == '.') {
                        yz[j][i] = brick.name;
                    } else {
                        yz[j][i] = '?';
                    }
                }
            }
        }
    }

    private boolean isRestingOn(int[] x1, int[] y1, int[] z1, int[] x2, int[] y2, int[] z2) {
        // if adjacent on z axis
        if (z1[1] + 1 == z2[0]) {
            if (x1[0] <= x2[1] && x2[0] <= x1[1] &&
                    y1[0] <= y2[1] && y2[0] <= y1[1]) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        init(input);
        int count = 0;
        for (Brick b : brickList) {
            HashSet<Brick> fallingBricks = new HashSet<>();
            countFallingBlocks(List.of(b), fallingBricks);
            count += fallingBricks.size();
        }
        return String.valueOf(count);
    }

    private void countFallingBlocks(List<Brick> allowedBricks, HashSet<Brick> fallingBricks) {
        List<Brick> newAllowedBricks = new ArrayList<>();
        for (Brick b : allowedBricks) {
            for (Brick supported : supportBrickMap.get(b)) {
                Set<Brick> someBricks = supportedBrickMap.get(supported);
              boolean verified = true;
                for (Brick verifyBrick : someBricks) {
                    if (!allowedBricks.contains(verifyBrick) && !fallingBricks.contains(verifyBrick)) {
                        verified = false;
                        break;
                    }
                }
                if (verified) {
                    fallingBricks.add(supported);
                    newAllowedBricks.add(supported);
                }
            }
        }
        if (!newAllowedBricks.isEmpty()) {
            countFallingBlocks(newAllowedBricks, fallingBricks);
        }
    }
}
