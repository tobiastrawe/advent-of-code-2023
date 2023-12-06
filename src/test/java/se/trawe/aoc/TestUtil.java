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

    public static Task getTaskForDay(int dayNumber) {
        try {
            Class<?> c = Class.forName("se.trawe.aoc.days.Day" + dayNumber);
            return (Task) c.getDeclaredMethod("getInstance", null).invoke(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static TestData getTestDataForDay(int day) {
        Yaml yml = new Yaml();
        InputStream inputStream = TestUtil.class.getClassLoader().getResourceAsStream("day" + day + "_test_data.yml");
        LinkedHashMap<String, Object> dataMap = yml.load(inputStream);
        LinkedHashMap<String, String> taskOneMap =  (LinkedHashMap<String, String>) dataMap.get(taskOne);
        LinkedHashMap<String, String> taskTwoMap = (LinkedHashMap<String, String>) dataMap.get(taskTwo);
        List<String> testDataTaskOne;
        List<String> testDataTaskTwo;
        if (dataMap.containsKey(data)) {
            testDataTaskOne = Arrays.stream(((String) dataMap.get(data)).split(testStringSplitter)).toList();
            testDataTaskTwo = testDataTaskOne;
        } else {
            testDataTaskOne = Arrays.stream(taskOneMap.get(data).split(testStringSplitter)).toList();
            testDataTaskTwo = Arrays.stream(taskTwoMap.get(data).split(testStringSplitter)).toList();
        }
        return new TestData(testDataTaskOne, taskOneMap.get(expected),
                testDataTaskTwo, taskTwoMap.get(expected));
    }
}