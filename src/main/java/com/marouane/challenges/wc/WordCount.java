package com.marouane.challenges.wc;

import java.io.File;
import java.util.stream.Stream;

public class WordCount {


    public static long countBytes(File f) {
        return f.length();
    }

    public static long countWords(Stream<String> lines) {
        return 0;
    }

    public static long countLines(Stream<String> lines) {
        long count = 0;
        try (lines) {
            count = lines.count();
        }
        return count;
    }
}
