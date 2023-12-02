package se.trawe.aoc.days;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {
    private final static TestData testData;

    static {
        try {
            testData = TestUtil.getTestDataForDay(Day1.getInstance().getDayNumber());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void runTaskOne() throws Exception {
        String result = Day1.getInstance().runTaskOne(testData.testDataTaskOne());
        assertEquals(testData.expectedTaskOne(), result);
    }

    @Test
    void runTaskTwo() {
        String result = Day1.getInstance().runTaskTwo(testData.testDataTaskTwo());
        assertEquals(testData.expectedTaskTwo(), result);
    }
}