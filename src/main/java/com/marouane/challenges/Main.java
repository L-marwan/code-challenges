package com.marouane.challenges;

import com.marouane.challenges.wc.WordCountCommand;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new WordCountCommand()).execute(args);
        System.exit(exitCode);
    }
}