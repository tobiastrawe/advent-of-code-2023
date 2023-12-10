package se.trawe.aoc.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public static void WriteToFile (String filename, char[][] screen) throws IOException {
        BufferedWriter outputWriter;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for (char[] chars : screen) {
            for (char aChar : chars) {
                outputWriter.write(aChar);
            }
            outputWriter.newLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }
}
