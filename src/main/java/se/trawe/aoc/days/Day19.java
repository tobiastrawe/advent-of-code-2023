package se.trawe.aoc.days;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 extends Task {

    private final static Day19 instance;

    static {
        instance = new Day19();
    }

    private Day19() {
    }

    @SuppressWarnings(value = "unused")
    public static Day19 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day19().run();
    }

    private final HashMap<String, Workflow> workflowHashMap = new HashMap<>();

    private final HashSet<MachinePart> machineParts = new HashSet<>();

    private record Workflow(ArrayList<Command> commands) {

        private String processMachinePart(MachinePart machinePart) {
            for (Command c : commands) {
                switch (c.xmas) {
                    case 'x' -> {
                        if (c.evaluate(machinePart.x)) return c.destination;
                    }
                    case 'm' -> {
                        if (c.evaluate(machinePart.m)) return c.destination;
                    }
                    case 'a' -> {
                        if (c.evaluate(machinePart.a)) return c.destination;
                    }
                    case 's' -> {
                        if (c.evaluate(machinePart.s)) return c.destination;
                    }
                    default -> {
                        return c.destination;
                    }
                }
            }
            return "error";
        }
    }

    private final HashSet<MachinePart> acceptedParts = new HashSet<>();
    private final HashSet<MachinePart> rejectedParts = new HashSet<>();


    private record Command(char xmas, boolean greaterThan, int value, String destination) {
        private boolean evaluate(int compareValue) {
            if (xmas == 'd') {
                return true;
            }
            return greaterThan ? compareValue > value : value > compareValue;
        }
    }

    private record MachinePart(int x, int m, int a, int s) {

        private int sum() {
            return x + m + a + s;
        }
    }

    private synchronized void init(List<String> input) {
        if (!workflowHashMap.isEmpty()) {
            return;
        }
        Pattern commandStart = Pattern.compile("^(\\w+).*");
        Pattern commandEnd = Pattern.compile(".*,(\\w+)}$");
        Pattern commandMiddle = Pattern.compile("([xmas])([>|<])(\\d+):(\\w+)");

        Pattern machinePartPattern = Pattern.compile("\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)}");
        for (String line : input) {
            Matcher commandStartMatcher = commandStart.matcher(line);
            if (commandStartMatcher.matches()) {
                var workFlowName = commandStartMatcher.group(1);
                var defaultDestination = commandEnd
                        .matcher(line)
                        .results()
                        .findFirst().get().group(1);
                Matcher commandMatcher = commandMiddle.matcher(line);
                ArrayList<Command> commands = new ArrayList<>();
                while (commandMatcher.find()) {
                    var xmas = commandMatcher.group(1).charAt(0);
                    boolean greaterThan = commandMatcher.group(2).equals(">");
                    int value = Integer.parseInt(commandMatcher.group(3));
                    var destination = commandMatcher.group(4);
                    commands.add(new Command(xmas, greaterThan, value, destination));
                }
                commands.add(new Command('d', true, 0, defaultDestination));
                workflowHashMap.put(workFlowName, new Workflow(commands));
            } else {
                Matcher machinePartMatcher = machinePartPattern.matcher(line);
                if (machinePartMatcher.matches()) {
                    int x = machinePartMatcher.group(1).transform(Integer::parseInt);
                    int m = machinePartMatcher.group(2).transform(Integer::parseInt);
                    int a = machinePartMatcher.group(3).transform(Integer::parseInt);
                    int s = machinePartMatcher.group(4).transform(Integer::parseInt);
                    machineParts.add(new MachinePart(x, m, a, s));
                }
            }
        }
    }

    @Override
    protected String runTaskOne(List<String> input) {
        init(input);
        for (MachinePart part : machineParts) {
            Workflow workflow = workflowHashMap.get("in");
            while (true) {
                String result = workflow.processMachinePart(part);
                if (result.equals("A")) {
                    acceptedParts.add(part);
                    break;
                } else if (result.equals("R")) {
                    rejectedParts.add(part);
                    break;
                } else {
                    workflow = workflowHashMap.get(result);
                }
            }
        }
        int sumOfAcceptedParts = acceptedParts.stream().mapToInt(MachinePart::sum).sum();
        return String.valueOf(sumOfAcceptedParts);
    }

    private record Ranges(int[] x, int[] m, int[] a, int[] s) {

        Ranges(Ranges copy) {
            this(new int[]{copy.x[0], copy.x[1]},
                    new int[]{copy.m[0], copy.m[1]},
                    new int[]{copy.a[0], copy.a[1]},
                    new int[]{copy.s[0], copy.s[1]});
        }

        private long possibleRanges() {
            return (long) (x[1] - x[0] + 1) * (m[1] - m[0] + 1) * (a[1] - a[0] + 1) * (s[1] - s[0] + 1);
        }

        private Ranges[] splitRange(char c, boolean greaterThan, int value) {
            Ranges[] split = new Ranges[2];
            Ranges rangeOne = new Ranges(this);
            Ranges rangeTwo = new Ranges(this);
            split[0] = rangeOne;
            split[1] = rangeTwo;
            switch (c) {
                case 'x' -> {
                    if (greaterThan) {
                        rangeOne.x[0] = value + 1;
                        rangeTwo.x[1] = value;
                    } else {
                        rangeOne.x[1] = value - 1;
                        rangeTwo.x[0] = value;
                    }
                }
                case 'm' -> {
                    if (greaterThan) {
                        rangeOne.m[0] = value + 1;
                        rangeTwo.m[1] = value;
                    } else {
                        rangeOne.m[1] = value - 1;
                        rangeTwo.m[0] = value;
                    }
                }
                case 'a' -> {
                    if (greaterThan) {
                        rangeOne.a[0] = value + 1;
                        rangeTwo.a[1] = value;
                    } else {
                        rangeOne.a[1] = value - 1;
                        rangeTwo.a[0] = value;
                    }
                }
                case 's' -> {
                    if (greaterThan) {
                        rangeOne.s[0] = value + 1;
                        rangeTwo.s[1] = value;
                    } else {
                        rangeOne.s[1] = value - 1;
                        rangeTwo.s[0] = value;
                    }
                }
            }
            return split;
        }
    }

    private final Set<Ranges> acceptedRanges = new HashSet<>();

    private void tracePath(Workflow workflow, Ranges range) {
        Ranges activeRange = new Ranges(range);
        for (Command c : workflow.commands) {
            Ranges[] split = activeRange.splitRange(c.xmas, c.greaterThan, c.value);
            if (c.destination.equals("A")) {
                if (c.xmas == 'd') {
                    acceptedRanges.add(new Ranges(activeRange));
                    return;
                } else {
                    acceptedRanges.add(split[0]);
                }
            } else if (c.destination.equals("R")) {
                if (c.xmas == 'd') {
                    return;
                }
            } else {
                tracePath(workflowHashMap.get(c.destination), split[0]);
            }
            activeRange = split[1];
        }
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        init(input);
        tracePath(workflowHashMap.get("in"), new Ranges(new int[]{1, 4000}, new int[]{1, 4000}, new int[]{1, 4000}, new int[]{1, 4000}));
        return String.valueOf(acceptedRanges.stream().mapToLong(Ranges::possibleRanges).sum());
    }
}
