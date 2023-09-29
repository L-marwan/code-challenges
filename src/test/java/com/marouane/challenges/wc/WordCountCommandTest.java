package com.marouane.challenges.wc;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class WordCountCommandTest {

    @Test
    void commandwithNoFlagsShouldPrintFullResult() throws URISyntaxException {
        var command = new WordCountCommand();
        CommandLine cmd = new CommandLine(command);

        StringWriter sw = new StringWriter();
        cmd.setOut(new PrintWriter(sw));

        var classloader = getClass().getClassLoader();

        var path = Paths.get(classloader.getResource("pg132.txt").toURI());
        int exitCode = cmd.execute("--", path.toString());

        assertThat(exitCode).isZero();
        assertThat(sw.toString()).isEqualTo("7145 \t58164 \t pg132.txt");
    }

    @Test
    void commandWithWordsFlagShouldPrintWordCountOnly() throws URISyntaxException {
        var command = new WordCountCommand();
        CommandLine cmd = new CommandLine(command);

        StringWriter sw = new StringWriter();
        cmd.setOut(new PrintWriter(sw));

        var classloader = getClass().getClassLoader();

        var path = Paths.get(classloader.getResource("pg132.txt").toURI());
        int exitCode = cmd.execute("-w", path.toString());

        assertThat(exitCode).isZero();
        assertThat(sw.toString()).isEqualTo(" \t58164 \t pg132.txt");
    }

    @Test
    void commandWithLinesFlagShouldPrintLineCountOnly() throws URISyntaxException {
        var command = new WordCountCommand();
        CommandLine cmd = new CommandLine(command);

        StringWriter sw = new StringWriter();
        cmd.setOut(new PrintWriter(sw));

        var classloader = getClass().getClassLoader();

        var path = Paths.get(classloader.getResource("pg132.txt").toURI());
        int exitCode = cmd.execute("-l", path.toString());

        assertThat(exitCode).isZero();
        assertThat(sw.toString()).isEqualTo("7145 \t \t pg132.txt");
    }

}