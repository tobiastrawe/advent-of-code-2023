package se.trawe.aoc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class InputGetter {

    public static List<String> getUrlContents(int i) {
        try {
            URL url = new URI("https://adventofcode.com/2023/day/" + i + "/input").toURL();
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Cookie", "session=53616c7465645f5fca027570238afda036f835287a7e868c52a518d38ab6ec0e6f6805e5f495e623d4d4c2566943da86f72a84b22f6622ea96a8f0e76ad96114");
            return new BufferedReader(new InputStreamReader(urlConnection.getInputStream())).lines().toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
