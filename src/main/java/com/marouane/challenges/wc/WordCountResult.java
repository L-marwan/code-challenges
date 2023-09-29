package com.marouane.challenges.wc;

import java.util.Objects;

public class WordCountResult {

    long words;
    long lines;
    long bytes;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordCountResult that = (WordCountResult) o;
        return words == that.words && lines == that.lines && bytes == that.bytes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(words, lines, bytes);
    }

    @Override
    public String toString() {
        return "{words=%d, lines=%d, bytes=%d}".formatted(words, lines, bytes);
    }
}
