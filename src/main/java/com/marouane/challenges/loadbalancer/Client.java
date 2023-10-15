package com.marouane.challenges.loadbalancer;

import org.apache.commons.lang3.time.StopWatch;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Client {

    public static void main(String[] args) throws InterruptedException {

        StopWatch watch = new StopWatch();
        ExecutorService threadPoolExecutor = Executors.newVirtualThreadPerTaskExecutor();

        // create many tasks

        var tasks = IntStream.range(0, 1_000)
                .mapToObj(i -> (Callable<Boolean>) () -> {
                    try {
                        System.out.println("Request: " + i);
                        sendRequest("http://localhost:7000/test");
                        return true;
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();

        watch.start();

        threadPoolExecutor.invokeAll(tasks);
        threadPoolExecutor.shutdown();
        watch.stop();
        System.out.println(watch.formatTime());
    }


    private static void sendRequest(String url) throws URISyntaxException {
        var req = HttpRequest
                .newBuilder(new URI(url))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();


        client.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
    }
}
