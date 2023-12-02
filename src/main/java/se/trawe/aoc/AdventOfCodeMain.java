package se.trawe.aoc;

public class AdventOfCodeMain {

    public static void main(String[] args) {
        for (int i = 1; i <= 25; i++) {
            try {
                Class<?> c = Class.forName("se.trawe.aoc.days.Day" + i);
                Task.register((Task) c.getDeclaredMethod("getInstance", null).invoke(null, null));
            } catch (ClassNotFoundException e) {
                System.out.println("Day " + i + " not found.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        Task.runAll();
    }
}
