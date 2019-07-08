package abbyylingvoexampleloader;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Loader {
    public static final File FILE_TO_WRITE = new File("output.txt");
    private static final String URL = "https://api.lingvolive.com/Translation/Examples?text=%s&dstLang=1049&srcLang=1033&pageSize=%s&startIndex=%s";
    private static final String CAN_NOT_WRITE_TO_FILE_MESSAGE = "Can't write to file \"%s\"";
    private static final String START_DOWNLOADING_MESSAGE = "Start downloading examples for the word \"%s\" from lingvolive.com ...";
    private static final String TITLE_MESSAGE = "These are examples for the word: \"%s\" downloaded from lingvolive.com. \n\n";
    /* params related messages */
    private static final String NO_USER_CONF_PARAMS_SUPPLIED_MESSAGE = "No user configuration parameters supplied! Using default ones.";
    private static final String NO_PARAMS_MESSAGE = "No input string supplied to look for examples! Exiting ...";
    private static final String ONLY_ONE_PARAM_SUPPLIED_MESSAGE = "Only one configuration parameter is supplied. Second one is set to default.";
    private static final String USED_PARAMS_MESSAGE = "Used parameters are: \"examplesPerThread\" = %s, \"threadNumber\" = %s";
    public static String inputString;
    private static int examplesPerThread = 200;
    private static int threadNumber = 4;

    public static void main(String[] args) {
        Date date = new Date();
        processParams(args);
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        int offset = 0;
        int threadCounter = 1;
        List<Runnable> tasks = new ArrayList<>();
        Task task;
        try {
            FileUtils.writeStringToFile(FILE_TO_WRITE, String.format(TITLE_MESSAGE, inputString), StandardCharsets.UTF_8);
        } catch (IOException e) {
            canNotWriteToFile(e);
        }
        System.out.println(String.format(START_DOWNLOADING_MESSAGE, inputString));
        while (threadCounter <= threadNumber) {
            task = new Task(buildUrl(offset), threadCounter);
            threadCounter++;
            tasks.add(task);
            threadPoolExecutor.execute(task);
            offset += examplesPerThread;
        }
        threadPoolExecutor.shutdown();
        printFinalStatistics(threadPoolExecutor, date);
    }

    /*
     * Print time it took to finish the global task to stdout.
     * */
    private static void printFinalStatistics(ThreadPoolExecutor threadPoolExecutor, Date date) {
        boolean isFinished = false;
        while (!isFinished) {
            try {
                isFinished = threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS);
            } catch (InterruptedException ignore) {
            }
        }
        System.out.println("Retrieving examples took: " + (new Date().getTime() - date.getTime()) / 1000 + " seconds");
    }

    public static void canNotWriteToFile(IOException e) {
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

