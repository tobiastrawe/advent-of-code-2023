package se.trawe.aoc.days;

import java.util.*;
import java.util.stream.Collectors;

import static se.trawe.aoc.util.MathUtil.lcm;

public class Day8 extends Task {

    private final static Day8 instance;

    static {
        instance = new Day8();
    }

    private Day8() {
    }

    @SuppressWarnings(value = "unused")
    public static Day8 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day8().run();
    }

    private record Node(String l, String r) {
    }

    @Override
    protected String runTaskOne(List<String> input) {
        final String instructions = input.get(0);
        final HashMap<String, Node> nodeMap = new HashMap<>();
        for (String line : input) {
            if (!line.contains("="))
                continue;
            String[] split = line.split("=");
            String target = split[1].replaceAll("\\W", "");
            nodeMap.put(split[0].replaceAll("\\W", ""),
                    new Node(target.substring(0, 3),
                            target.substring(3, 6)));
        }

        return String.valueOf(calcLowestNumberOfSteps(nodeMap, instructions, "AAA"));
    }
    private long calcLowestNumberOfSteps(HashMap<String, Node> nodeMap, String instructions, String startNodeEnding) {
        Set<String> positions = nodeMap.keySet().stream().filter(p -> p.endsWith(startNodeEnding)).collect(Collectors.toSet());
        String[] splitStructions = instructions.split("");
        Iterator<String> iterable = Arrays.stream(splitStructions).iterator();
        String instruction = "";
        int counter = 0;
        long numberOfSteps = 1;

        while (!positions.isEmpty()) {
            if (iterable.hasNext()) {
                instruction = iterable.next();
            } else {
                iterable = Arrays.stream(splitStructions).iterator();
                instruction = iterable.next();
            }
            Set<String> newPositions = new HashSet<>();
            for (String s : positions) {
                if (s.endsWith("Z")) {
                    numberOfSteps = lcm(numberOfSteps, counter, false);
                } else {
                    Node n = nodeMap.get(s);
                    newPositions.add(instruction.equals("L") ? n.l : n.r);
                }
            }
            counter += 1;
            positions = newPositions;
        }
        return numberOfSteps;
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        final String instructions = input.get(0);
        final HashMap<String, Node> nodeMap = new HashMap<>();
        for (String line : input) {
            if (!line.contains("="))
                continue;
            String[] split = line.split("=");
            String target = split[1].replaceAll("[^0-9a-zA-Z]", "");
            nodeMap.put(split[0].replaceAll("[^0-9a-zA-Z]", ""),
                    new Node(target.substring(0, 3),
                            target.substring(3, 6)));
        }
        return String.valueOf(calcLowestNumberOfSteps(nodeMap, instructions, "A"));
    }
}
