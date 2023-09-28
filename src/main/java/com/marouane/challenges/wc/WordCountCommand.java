package com.marouane.challenges.wc;

import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "jwc", mixinStandardHelpOptions = true, version = "jwc 0.1",
        description = "insert desc here")
public class WordCountCommand implements Callable<Integer> {
    @CommandLine.Parameters(index = "0" )
    private File file;

    @CommandLine.Option(names = "-w")
    private boolean countWords;

    @Override
    public Integer call() throws Exception {
        return null;
    }
}
