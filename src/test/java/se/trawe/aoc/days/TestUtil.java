package se.trawe.aoc.days;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class TestUtil {

    public static TestData getTestDataForDay(int day) {
        Yaml yml = new Yaml();
        InputStream inputStream = TestUtil.class.getClassLoader().getResourceAsStream("day" + day + "_test_data.yml");
        LinkedHashMap<String, LinkedHashMap<String, String>> dataMap = yml.load(inputStream);
        LinkedHashMap<String, String> taskOneMap = dataMap.get("task-one");
        LinkedHashMap<String, String> taskTwoMap = dataMap.get("task-two");
        return new TestData(Arrays.stream(taskOneMap.get("data").split(" ")).toList(), taskOneMap.get("expected"),
                Arrays.stream(taskTwoMap.get("data").split(" ")).toList(), taskTwoMap.get("expected"));
    }
}
