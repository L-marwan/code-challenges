package com.marouane.challenges.huffman;

import java.util.HashMap;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class HuffmanCompressionHelper {


    public static Queue<Occurrence> getCharacterPriorityQueue(String testStr) {
        var occurrences = new PriorityQueue<Occurrence>();
        var m = new HashMap<Character, Integer>();

        for (char c : testStr.toCharArray()) {
            m.merge(c, 1, Integer::sum);
        }
        m.forEach((character, count) -> occurrences.add(new Occurrence(character, count)));
        return occurrences;
    }


    static class Occurrence implements Comparable<Occurrence> {
        int occurrenceCount;
        char character;

        public Occurrence(char character, int occurrenceCount) {
            this.occurrenceCount = occurrenceCount;
            this.character = character;

        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Occurrence that = (Occurrence) o;
            return occurrenceCount == that.occurrenceCount && character == that.character;
        }

        @Override
        public int hashCode() {
            return Objects.hash(occurrenceCount, character);
        }

        @Override
        public int compareTo(Occurrence o) {
            return this.occurrenceCount - o.occurrenceCount;
        }
    }
}
