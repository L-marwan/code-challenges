package com.marouane.challenges.wc;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


class WordCountTest {

    @Test
    void countBytesTest() throws Exception {
        Stream<String> lines = getStringStream();
        var classloader = getClass().getClassLoader();
        Path path = Paths.get(classloader.getResource("pg132.txt").toURI());
        assertThat(WordCount.countBytes(path.toFile())).isEqualTo(342190);
    }

    @Test
    void countLinesTest() throws Exception {
        Stream<String> lines = getStringStream();

        assertThat(WordCount.countLines(Stream.of("hello", "world"))).isEqualTo(2);
        assertThat(WordCount.countLines(Stream.of())).isZero();
        assertThat(WordCount.countLines(lines)).isEqualTo(7145);
    }

    @Test
    void countWordsTest() throws Exception {
        Stream<String> lines = getStringStream();
        assertThat(WordCount.countWords(lines)).isEqualTo(58159);
    }

    private Stream<String> getStringStream() throws Exception {
        var classloader = getClass().getClassLoader();
        Path path = Paths.get(classloader.getResource("pg132.txt").toURI());
        Stream<String> lines = Files.lines(path);
        return lines;
    }
}