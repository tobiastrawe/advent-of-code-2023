package se.trawe.aoc.days;

import org.junit.jupiter.api.Test;
import se.trawe.aoc.TestData;
import se.trawe.aoc.TestUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day20Test {
    private final static TestData testData;
    private static final Task day;

    static {
        try {
            int dayNumber = Integer.parseInt(Day20Test.class.getSimpleName().replaceAll("\\D", ""));
            day = Task.getTask(dayNumber);
            testData = TestUtil.getTestDataForDay(dayNumber);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void runTaskOne() {
        List<String> testOne = testData.testDataTaskOne().subList(0, 5);
        List<String> testTwo = testData.testDataTaskOne().subList(6, testData.testDataTaskOne().size());
        String resultOne = day.runTaskOne(testData.testDataTaskOne());
        assertEquals(32000000, resultOne);
        String resultTwo = day.runTaskOne(testData.testDataTaskOne());
        assertEquals(testData.expectedTaskOne(), resultTwo);
    }

    @Test
    void runTaskTwo() {
        String result = day.runTaskTwo(testData.testDataTaskTwo());
        assertEquals(testData.expectedTaskTwo(), result);
    }
}