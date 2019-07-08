package abbyylingvoexampledownloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import abbyylingvoexampledownloader.jsonentity.Row;
import abbyylingvoexampledownloader.jsonentity.Wrapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.*;

import static abbyylingvoexampledownloader.Downloader.FILE_TO_WRITE;

public class Task implements Runnable {
    private static final String ITEM_PATTERN = "%s. %s\n%s\n";
    private static final String THREAD_FINISHED_DOWNLOADING_MESSAGE = "Thread with id %s has finished downloading examples.";
    private static final String THREAD_STARTS_WRITING_MESSAGE = "Thread with id %s starts writing examples to the file: \"%s\"";
    private static final String STATISTICS_MESSAGE = "Number of examples written to the file: more or equal %s";
    private static final String JSON_PARSING_FAIL_MESSAGE = "Thread with id %s couldn't parse examples for the word \"%s\" acquired from the url: %s";
    private static int counter = 1;
    private static StringBuffer stringBuffer;
    private final int ID;
    private final String URL;

    public Task(String url, StringBuffer stringBuffer, int id) {
        this.URL = url;
        this.stringBuffer = stringBuffer;
        this.ID = id;
    }

    synchronized static private void writeExamplesToFile(Wrapper wrapper, int id) {
        System.out.println(String.format(THREAD_STARTS_WRITING_MESSAGE, id, FILE_TO_WRITE));
        for (Row row : wrapper.getRows()) {
            printIntermediateStatistics();
            stringBuffer.append(buildExampleItem(row));
            incrementCounter();
        }
    }

    /*
     * Notify upon downloading every 1000 examples.
     * Print the message to stdout.
     * */
    private static void printIntermediateStatistics() {
        int counter = getCounter();
        if (counter % 1000 == 0) {
            System.out.println(String.format(STATISTICS_MESSAGE, counter));
        }
    }

    synchronized private static int getCounter() {
        return counter;
    }

    synchronized private static void incrementCounter() {
        counter++;
    }

    private static String buildExampleItem(Row row) {
        return String.format(ITEM_PATTERN, getCounter(), row.getSourceFragment().getText(), row.getTargetFragment().getText());
    }

    public void run() {
        Wrapper examplesWrapper = downloadExamples();
        writeExamplesToFile(examplesWrapper, ID);
    }

    private Wrapper downloadExamples() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        HttpEntity<String> request = new HttpEntity<>(headers);
        String res = restTemplate.exchange(URL, HttpMethod.GET, request, String.class).getBody();
        Wrapper examplesWrapper = parse(res);
        System.out.println(String.format(THREAD_FINISHED_DOWNLOADING_MESSAGE, ID));
        return examplesWrapper;
    }

    private Wrapper parse(String res) {
        ObjectMapper mapper = new ObjectMapper();
        Wrapper wrapper = null;
        try {
            wrapper = mapper.readValue(res, Wrapper.class);
        } catch (IOException e) {
            System.out.println(String.format(JSON_PARSING_FAIL_MESSAGE, ID, Downloader.inputString, URL));
            e.printStackTrace();
            System.exit(0);
        }
        return wrapper;
    }
}