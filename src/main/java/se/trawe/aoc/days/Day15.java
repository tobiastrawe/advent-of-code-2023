package se.trawe.aoc.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 extends Task {

    private final static Day15 instance;

    static {
        instance = new Day15();
    }

    private Day15() {
    }

    @SuppressWarnings(value = "unused")
    public static Day15 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Day15().run();
    }

    @Override
    protected String runTaskOne(List<String> input) {
        List<Integer> results = new ArrayList<>();
        for (String code : input.get(0).split(",")) {
            var value = hashValue(code);
            results.add(value);
        }
        return String.valueOf(results.stream().mapToInt(value -> value).sum());
    }

    private static int hashValue(String code) {
        int value = 0;
        for (char c : code.toCharArray()) {
            value = ((value + (int) c) * 17) % 256;
        }
        return value;
    }

    private record Command(String label, int boxNumber, boolean put, int focalStrength) {}

    private record Lens(String label, int focalStrength) {}

    @Override
    protected String runTaskTwo(List<String> input) {
        List<Command> commands = new ArrayList<>();
        HashMap<Integer, ArrayList<Lens>> boxContents = new HashMap<>();
        Pattern p = Pattern.compile("(\\w+)([=|-])(\\d*)");
        for (String code : input.get(0).split(",")) {
            Matcher m = p.matcher(code);
            if (m.matches()) {
                String label = m.group(1);
                int boxNumber = hashValue(label);
                String operator = m.group(2);
                int focalStrength = -1;
                boolean put = true;
                if (operator.equals("=")) {
                    focalStrength = Integer.parseInt(m.group(3));
                } else {
                    put = false;
                }
                commands.add(new Command(label, boxNumber, put, focalStrength));
            }
        }
        int commandsRun = 0;
        for (Command c : commands) {
            commandsRun++;
            ArrayList<Lens> contents = boxContents.get(c.boxNumber);
            Lens newLens = new Lens(c.label, c.focalStrength);
            if (c.put) {
                if (contents == null) {
                    contents = new ArrayList<>();
                    boxContents.put(c.boxNumber, contents);
                    contents.add(newLens);
                } else {
                    addOrReplaceBox(c.label, contents, newLens);
                }
            } else {
                if (contents != null) {
                    contents.removeIf(l -> l.label.equals(c.label));
                }
            }
        }
        long values = 0;
        for (Integer i : boxContents.keySet()) {
            ArrayList<Lens> contents = boxContents.get(i);
            for (int j = 0; j < contents.size(); j++) {
                Lens l = contents.get(j);
                values += (long) (i + 1) * (j +1) * l.focalStrength;
            }
        }
        return String.valueOf(values);
    }

    private static void addOrReplaceBox(String label, ArrayList<Lens> box, Lens newLens) {
        for (Lens l : box) {
            if (l.label.equals(label)) {
                box.set(box.indexOf(l), newLens);
                return;
            }
        }
        box.add(newLens);
    }
}
