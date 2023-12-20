package se.trawe.aoc.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private record Workflow(ArrayList<Command> commands) {}

    private record Command(char xmas, boolean greaterThan, int value, String destination) {
        private boolean evaluate(char compareChar, int compareValue) {
            if (xmas == 'd') {
                return true;
            }
            if (compareChar != xmas) {
                throw new RuntimeException("wrong char supplied to command");
            }
            return greaterThan ? compareValue > value : value > compareValue;
        }
    }

    private synchronized void init(List<String> input) {
        Pattern commandStart = Pattern.compile("^(\\w+).*");
        Pattern commandEnd = Pattern.compile(".*,(\\w+)}$");
        Pattern commandMiddle = Pattern.compile("([xmas])([>|<])(\\d+):(\\w+)");
        for (String line : input) {
            Matcher m = commandStart.matcher(line);
            if (m.matches()) {
                var workFlowName = m.group(1);
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
            }
        }
    }

    @Override
    protected String runTaskOne(List<String> input) {
        init(input);
        return "no result";
    }

    @Override
    protected String runTaskTwo(List<String> input) {
        return "no result";
    }


}
