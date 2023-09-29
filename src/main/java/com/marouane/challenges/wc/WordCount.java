package com.marouane.challenges.wc;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WordCount {

    public static WordCountResult count(InputStream inputStream) throws IOException {
        var result = new WordCountResult();
        try (inputStream) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            var lines = reader.lines();
            lines.forEach((line -> countWordsAndLines(line, result)));
            return result;
        }
    }

    private static void countWordsAndLines(String line, WordCountResult result) {
        result.lines++;
        if (StringUtils.isNotBlank(line))
            result.words += line.strip().split("\\s+").length;
    }

}
