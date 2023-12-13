package se.trawe.aoc;

import org.yaml.snakeyaml.Yaml;
import se.trawe.aoc.days.Task;

import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class TestUtil {

    private static final String testStringSplitter = "\n";
    private static final String taskOne = "task-one";
    private static final String taskTwo = "task-two";
    private static final String expected = "expected";
    private static final String data = "data";

    @SuppressWarnings("unchecked")
    public static TestData getTestDataForDay(int day) {
        Yaml yml = new Yaml();
        InputStream inputStream = TestUtil.class.getClassLoader().getResourceAsStream("day" + day + "_test_data.yml");
        LinkedHashMap<String, Object> dataMap = yml.load(inputStream);
        LinkedHashMap<String, String> taskOneMap =  (LinkedHashMap<String, String>) dataMap.get(taskOne);
        LinkedHashMap<String, String> taskTwoMap = (LinkedHashMap<String, String>) dataMap.get(taskTwo);
        List<String> testDataTaskOne;
        List<String> testDataTaskTwo;
        if (taskOneMap.containsKey(data)) {
            testDataTaskOne = Arrays.stream(taskOneMap.get(data).split(testStringSplitter)).toList();
        } else {
            testDataTaskOne = Arrays.stream(((String) dataMap.get(data)).split(testStringSplitter)).toList();
        }
        if (taskTwoMap.containsKey(data)) {
            testDataTaskTwo = Arrays.stream(taskTwoMap.get(data).split(testStringSplitter)).toList();
        } else {
            testDataTaskTwo = testDataTaskOne;
        }
        return new TestData(testDataTaskOne, taskOneMap.get(expected),
                testDataTaskTwo, taskTwoMap.get(expected));
    }
}