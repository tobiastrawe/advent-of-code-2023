package se.trawe.aoc.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {
    private final static TestData testData;

    static {
        try {
            testData = TestUtil.getTestDataForDay(Day2.getInstance().getDayNumber());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void runTaskOne() throws Exception {
        String result = Day2.getInstance().runTaskOne(testData.testDataTaskOne());
        assertEquals(testData.expectedTaskOne(), result);
    }

    @Test
    void runTaskTwo() {
        String result = Day2.getInstance().runTaskTwo(testData.testDataTaskTwo());
        assertEquals(testData.expectedTaskTwo(), result);
    }
}