package com.marouane.challenges.wc;

import picocli.CommandLine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "jwc", mixinStandardHelpOptions = true, version = "jwc 0.1",
        description = "insert desc here")
public class WordCountCommand implements Callable<Integer> {

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Parameters(index = "0")
    private File file;

    @CommandLine.Option(names = "-w")
    private boolean countWords;
    @CommandLine.Option(names = "-l")
    private boolean countLines;

    //reference to the result to make testing easier
    private WordCountResult result;

    @Override
    public Integer call() {
        InputStream is = null;
        try {
            // if no file is specified we attempt reading from piped input
            is = file != null ? new FileInputStream(file) : System.in;
            result = WordCount.count(is);

            // using the printwriter provided by PicoCLI to make testing easier

            spec.commandLine().getOut().print(formatResult());
        } catch (IOException e) {
            spec.commandLine().getErr().print("something went wrong: " + e.getLocalizedMessage());
            return -1;
        }
        return 0;
    }

    private String formatResult() {
        String resultString = "%s \t%s \t %s";
        String fileName = file != null ? file.getName() : "";

        String words = countWords ? "" + result.words : "";
        String lines = countLines ? "" + result.lines : "";

        if (!countWords && !countLines) { // no options provided we print both results
            words = String.valueOf(result.words);
            lines = String.valueOf(result.lines);
        }

        return resultString.formatted(lines, words, fileName);
    }
}
