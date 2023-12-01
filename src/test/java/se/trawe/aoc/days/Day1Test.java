package se.trawe.aoc.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {

    private static String inputTestOne = "1abc2\n" +
            "pqr3stu8vwx\n" +
            "a1b2c3d4e5f\n" +
            "treb7uchet";

    private static String inputTestTwo = "two1nine\n" +
            "eightwothree\n" +
            "abcone2threexyz\n" +
            "xtwone3four\n" +
            "4nineeightseven2\n" +
            "zoneight234\n" +
            "7pqrstsixteen";

    private final int expectedTestOne = 142;
    private final int expectedTestTwo = 281;
    @Test
    void runTestOne() {
        int output = Day1.getInstance().runTestOne(inputTestOne);
        assertEquals(expectedTestOne, output);
    }

    @Test
    void runTestTwo() {
        int output = Day1.getInstance().runTestTwo(inputTestTwo);
        assertEquals(expectedTestTwo, output);
    }
}