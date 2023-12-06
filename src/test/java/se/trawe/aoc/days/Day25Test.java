package se.trawe.aoc.days;

import org.junit.jupiter.api.Test;
import se.trawe.aoc.TestData;
import se.trawe.aoc.TestUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day25Test {
    private final static TestData testData;
    private static final Task day;

    static {
        try {
            int dayNumber = Integer.parseInt(Day25Test.class.getSimpleName().replaceAll("\\D", ""));
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