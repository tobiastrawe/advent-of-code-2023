package se.trawe.aoc.days;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class TestUtil {

    private static final String testStringSplitter = "\n";
    private static final String taskOne = "task-one";
    private static final String taskTwo = "task-two";
    private static final String expected = "expected";
    private static final String data = "data";

    public static TestData getTestDataForDay(int day) {
        Yaml yml = new Yaml();
        InputStream inputStream = TestUtil.class.getClassLoader().getResourceAsStream("day" + day + "_test_data.yml");
        LinkedHashMap<String, LinkedHashMap<String, String>> dataMap = yml.load(inputStream);
        LinkedHashMap<String, String> taskOneMap = dataMap.get(taskOne);
        LinkedHashMap<String, String> taskTwoMap = dataMap.get(taskTwo);
        return new TestData(Arrays.stream(taskOneMap.get(data).split(testStringSplitter)).toList(), taskOneMap.get(expected),
                Arrays.stream(taskTwoMap.get(data).split(testStringSplitter)).toList(), taskTwoMap.get(expected));
    }
}
