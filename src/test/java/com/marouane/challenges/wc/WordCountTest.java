package com.marouane.challenges.wc;

import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class WordCountTest {
    @Test
    void countTest() throws Exception {
        var classloader = getClass().getClassLoader();
        InputStream is = classloader.getResourceAsStream("emptylines.txt");

        var expected = new WordCountResult();
        expected.words = 0;
        expected.lines = 4;
        assertThat(WordCount.count(is)).isEqualTo(expected);

        is = classloader.getResourceAsStream("pg132.txt");

        expected.words = 58164;
        expected.lines = 7145;
        assertThat(WordCount.count(is)).isEqualTo(expected);

    }
}