package se.trawe.aoc;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Task implements Comparable<Task> {

    private static Pattern comparePattern = Pattern.compile("^Day(\\d+)$");

    private static final SortedSet<Task> tasks = new TreeSet<>();

    protected static void register(Task task) {
        tasks.add(task);
    }

    protected abstract void run();

    protected int getDayNumber() throws Exception {
        Matcher m = comparePattern.matcher(this.getClass().getSimpleName());
        if (m.matches()) {
            return Integer.parseInt(m.group(1));
        }
        throw new Exception("Illegal class name for sorting purposes.");
    }

    public static void runAll() {
        tasks.forEach(Task::run);
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
