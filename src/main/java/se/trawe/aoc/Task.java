package se.trawe.aoc;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Task implements Comparable<Task> {

    private static final Pattern comparePattern = Pattern.compile("^Day(\\d+)$");

    private static final SortedSet<Task> tasks = new TreeSet<>();

    protected static void register(Task task) {
        tasks.add(task);
    }

    public static void runAll() {
        tasks.forEach(Task::run);
    }

    protected abstract String runTaskOne(List<String> input);

    protected abstract String runTaskTwo(List<String> input);

    protected final void run() {
        final List<String> input;
        try {
            input = InputGetter.getUrlContents(getDayNumber());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Result result = new Result(this.getClass().getSimpleName(), runTaskOne(input), runTaskTwo(input));
        System.out.println(result);
    }

    public int getDayNumber() throws Exception {
        Matcher m = comparePattern.matcher(this.getClass().getSimpleName());
        if (m.matches()) {
            return Integer.parseInt(m.group(1));
        }
        throw new Exception("Illegal class name for sorting purposes.");
    }

    @Override
    public int compareTo(Task t) {
        try {
            return this.getDayNumber() - t.getDayNumber();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
