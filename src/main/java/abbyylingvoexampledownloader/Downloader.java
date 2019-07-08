package abbyylingvoexampledownloader;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * Concurrent downloader of examples from lingvolive.com
 * */
public class Downloader {
    public static final File FILE_TO_WRITE = new File("output.txt");
    private static final String URL = "https://api.lingvolive.com/Translation/Examples?text=%s&dstLang=1049&srcLang=1033&pageSize=%s&startIndex=%s";
    /* params related messages */
    private static final String NO_USER_CONF_PARAMS_SUPPLIED_MESSAGE = "No user configuration parameters supplied! Using default ones.";
    private static final String NO_PARAMS_MESSAGE = "No input string supplied to look for examples! Exiting ...";
    private static final String ONLY_ONE_PARAM_SUPPLIED_MESSAGE = "Only one configuration parameter is supplied. Second one is set to default.";
    private static final String USED_PARAMS_MESSAGE = "Used parameters are: \"examplesPerThread\" = %s, \"threadNumber\" = %s";
    /* other messages */
    private static final String CAN_NOT_WRITE_TO_FILE_MESSAGE = "Can't write to file \"%s\"";
    private static final String START_DOWNLOADING_MESSAGE = "Start downloading examples for the word \"%s\" from lingvolive.com ...";
    private static final String TITLE_MESSAGE = "These are examples for the word: \"%s\" downloaded from lingvolive.com. \n\n";
    private static final String FINAL_STATISTICS_MESSAGE = "Acquiring examples took: %s seconds";
    public static String inputString;
    private static Date startAtDate;
    private static int examplesPerThread = 200;
    private static int threadNumber = 4;

    /*
     * First argument is a word or phrase to look for in contexts.
     * Second argument is a number of examples per thread.
     * Third argument is a number of threads.
     * */
    public static void main(String[] args) {
        noteStartTime();
        processParams(args);
        ExecutorService executorService = Executors.newCachedThreadPool();
        StringBuffer examplesBuffer = new StringBuffer();
        startAllDownloadTasks(executorService, examplesBuffer);
        waitUntilAllTasksFinish(executorService);
        writeStringToFile(examplesBuffer.toString(), true);
        printFinalStatistics();
    }

    private static void startAllDownloadTasks(ExecutorService executorService, StringBuffer examplesBuffer) {
        int offset = 0;
        int threadCounter = 1;
        List<Runnable> tasks = new ArrayList<>();
        Task task;
        writeStringToFile(String.format(TITLE_MESSAGE, inputString), false);
        System.out.println(String.format(START_DOWNLOADING_MESSAGE, inputString));
        while (threadCounter <= threadNumber) {
            task = new Task(buildUrl(offset), examplesBuffer, threadCounter);
            threadCounter++;
            tasks.add(task);
            executorService.execute(task);
            offset += examplesPerThread;
        }
        executorService.shutdown();
    }

    private static void noteStartTime() {
        startAtDate = new Date();
    }

    private static void writeStringToFile(String string, boolean append) {
        try {
            FileUtils.writeStringToFile(FILE_TO_WRITE, string, StandardCharsets.UTF_8, append);
        } catch (IOException e) {
            canNotWriteToFile(e);
        }
    }

    private static void waitUntilAllTasksFinish(ExecutorService executorService) {
        boolean isFinished = false;
        while (!isFinished) {
            try {
                isFinished = executorService.awaitTermination(60, TimeUnit.SECONDS);
            } catch (InterruptedException ignore) {
            }
        }
    }

    /*
     * Print time it took to finish the global task to stdout.
     * */
    private static void printFinalStatistics() {
        long totalTimeInSec = (new Date().getTime() - startAtDate.getTime()) / 1000;
        System.out.println(String.format(FINAL_STATISTICS_MESSAGE, totalTimeInSec));
    }

    private static void canNotWriteToFile(IOException e) {
        System.out.println(String.format(CAN_NOT_WRITE_TO_FILE_MESSAGE, FILE_TO_WRITE.getAbsolutePath()));
        e.printStackTrace();
        System.exit(0);
    }

    private static String buildUrl(int offset) {
        return String.format(URL, inputString, examplesPerThread, offset);
    }

    private static void processParams(String[] args) {
        if (args.length == 0) {
            System.out.println(NO_PARAMS_MESSAGE);
            System.exit(0);
        } else if (args.length == 1) {
            inputString = args[0];
            System.out.println(String.format(NO_USER_CONF_PARAMS_SUPPLIED_MESSAGE));
            System.out.println(String.format(USED_PARAMS_MESSAGE, examplesPerThread, threadNumber));
        } else if (args.length == 2) {
            inputString = args[0];
            examplesPerThread = Integer.parseInt(args[1]);
            System.out.println(ONLY_ONE_PARAM_SUPPLIED_MESSAGE);
            System.out.println(String.format(USED_PARAMS_MESSAGE, examplesPerThread, threadNumber));
        } else {
            inputString = args[0];
            examplesPerThread = Integer.parseInt(args[1]);
            threadNumber = Integer.parseInt(args[2]);
            System.out.println(String.format(USED_PARAMS_MESSAGE, examplesPerThread, threadNumber));
        }
    }
}