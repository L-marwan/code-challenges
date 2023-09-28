package com.marouane.challenges.wc;

import java.io.*;
import java.util.stream.Stream;

public class WordCount {

    public static WordCountResult count(InputStream inputStream) throws IOException {
        var result = new WordCountResult();
        try (inputStream) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            var lines = reader.lines();
            lines.forEach((line -> countEverything(line, result)));
            return result;
        }
    }

    private static void countEverything(String line, WordCountResult result) {
        result.lines++;
        result.bytes += line.getBytes().length;
        result.words += line.strip().split("\\s+").length;
    }

    public static long countBytes(File f) {
        return f.length();
    }

    public static long countBytes(InputStream inputStream) throws IOException {
        try (inputStream) {
            byte[] buffer = new byte[1024];
            int chunkBytesRead = 0;
            int length = 0;
            while ((chunkBytesRead = inputStream.read(buffer)) != -1) {
                length += chunkBytesRead;
            }
            return length;
        }
    }

    public static long countWords(Stream<String> lines) {
        WordCountResult res = new WordCountResult();

        lines.forEach(line -> countEverything(line, res));
        return res.words;
    }

    public static long countLines(Stream<String> lines) {
        long count = 0;
        try (lines) {
            count = lines.count();
        }
        return count;
    }

    public static long countLines(InputStream inputStream) throws IOException {

        return count(inputStream).lines;

    }
}
