package com.marouane.challenges.huffman;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.PriorityQueue;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;

class HuffmanCompressionHelperTest {

    @Test
    void getPriorityCharacterQueueTest() {
        var testStr = "test";

        var expected = new PriorityQueue<HuffmanCompressionHelper.Occurrence>();
        expected.add(new HuffmanCompressionHelper.Occurrence('t', 2));
        expected.add(new HuffmanCompressionHelper.Occurrence('e', 1));
        expected.add(new HuffmanCompressionHelper.Occurrence('s', 1));

        Queue<HuffmanCompressionHelper.Occurrence> actual = HuffmanCompressionHelper.getCharacterPriorityQueue(testStr);

        assertThat(actual).containsAll(expected);

        var classloader = getClass().getClassLoader();
        InputStream is = classloader.getResourceAsStream("emptylines.txt");



    }

}