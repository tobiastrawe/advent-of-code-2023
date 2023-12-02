package se.trawe.aoc;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
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
