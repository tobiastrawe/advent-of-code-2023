package se.trawe.aoc.days;

import org.junit.jupiter.api.Test;
import se.trawe.aoc.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {
    private final static TestData testData;
    private static final Task day;

    static {
        try {
            int dayNumber = Integer.parseInt(Day4Test.class.getSimpleName().replaceAll("\\D", ""));
            day = Task.getTaskByDayNumber(dayNumber);
            testData = TestUtil.getTestDataForDay(dayNumber);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void runTaskOne() {
        String result = day.runTaskOne(testData.testDataTaskOne());
        assertEquals(testData.expectedTaskOne(), result);
    }

    @Test
    void runTaskTwo() {
        String result = day.runTaskTwo(testData.testDataTaskTwo());
        assertEquals(testData.expectedTaskTwo(), result);
    }
}