package se.trawe.aoc;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class InputGetter {

    private static String session = getSession();

    static String getSession() {
        try {
            return session = new BufferedReader(new InputStreamReader(Objects.requireNonNull(InputGetter.class.getClassLoader().getResourceAsStream("session.txt")))).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getFileContents(int day) {
        InputStream inputStream = InputGetter.class.getClassLoader().getResourceAsStream("day" + day + "_data.yml");
        if (inputStream == null) {
            return null;
        }
        Yaml yml = new Yaml();
        LinkedHashMap<String, String> loadedData = yml.load(inputStream);
        return Arrays.stream(loadedData.get("data").split("\n")).toList();
    }

    public static List<String> getUrlContents(int i) {
        try {
            URL url = new URI("https://adventofcode.com/2023/day/" + i + "/input").toURL();
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Cookie", session);
            return new BufferedReader(new InputStreamReader(urlConnection.getInputStream())).lines().toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
