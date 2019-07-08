package abbyylingvoexampleloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import abbyylingvoexampleloader.jsonentity.Row;
import abbyylingvoexampleloader.jsonentity.Wrapper;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static abbyylingvoexampleloader.Loader.FILE_TO_WRITE;

public class Task implements Runnable {
    private static final String THREAD_FINISHED_MESSAGE = "Thread with id %s has finished downloading examples and now starts writing them to the file: \"%s\"";
    private static final String STATISTICS_MESSAGE = "Number of examples written to the file: more or equal %s";
    private static final String URL_FAIL_MESSAGE = "Thread with id %s couldn't download examples for the word \"%s\" using url: %s";
    private static final String ITEM_PATTERN = "%s. %s\n%s\n";
    private static int i = 0;
    private final String URL;
    private final int ID;

    public Task(String URL, int ID) {
        this.URL = URL;
        this.ID = ID;
    }

    synchronized static private void print(Wrapper wrapper) {
        try {
            for (Row row : wrapper.getRows()) {
                FileUtils.writeStringToFile(FILE_TO_WRITE, buildText(row), StandardCharsets.UTF_8, true);
                printIntermediateStatistics();
            }
        } catch (IOException e) {
            Loader.canNotWriteToFile(e);
        }
    }

    private static String buildText(Row row) {
        return String.format(ITEM_PATTERN, i++, row.getSourceFragment().getText(), row.getTargetFragment().getText());
    }

    /*
     * Notify upon downloading every 1000 examples.
     * Print the message to stdout.
     * */
    private static void printIntermediateStatistics() {
        if (i % 1000 == 0) {
            System.out.println(String.format(STATISTICS_MESSAGE, i));
        }
    }

    public void run() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            String res = restTemplate.exchange(URL, HttpMethod.GET, request, String.class).getBody();
            Wrapper examplesWrapper = parse(res);
            System.out.println(String.format(THREAD_FINISHED_MESSAGE, ID, FILE_TO_WRITE));
            print(examplesWrapper);
        } catch (RestClientException | IOException e) {
            System.out.println(String.format(URL_FAIL_MESSAGE, ID, Loader.inputString, URL));
            e.printStackTrace();
        }
    }

    private Wrapper parse(String res) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(res, Wrapper.class);
    }
}