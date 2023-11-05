package com.marouane.challenges.loadbalancer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class LoadBalancer {


    public static void main(String[] args) throws IOException {
        var server = getServer("localhost", 7000, "/");

        server.start();
        System.out.println("Loadbalancer started on port 7000");
    }

    private static HttpServer getServer(String hostname, int port, String path) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(hostname, port), 0);

        ExecutorService threadPoolExecutor = Executors.newVirtualThreadPerTaskExecutor();

        server.createContext(path, new MyHttpHandler(RoundRobinLoadBalancerAlgorithm.getInstance()));
        server.setExecutor(threadPoolExecutor);
        return server;
    }

    static class MyHttpHandler implements HttpHandler {

        private LoadBalancerAlgorithm algo;
        Logger logger = Logger.getLogger(this.getClass().toString());

        public MyHttpHandler(LoadBalancerAlgorithm algo) {
            this.algo = algo;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            logger.info("handle request %s ".formatted(httpExchange.getRequestURI()));
            String requestParamValue = null;
            if ("GET".equals(httpExchange.getRequestMethod())) {
                requestParamValue = httpExchange.getRequestURI().toString();
                handleResponse(httpExchange, requestParamValue);
            } else {
                handleNotSupported(httpExchange);
            }

        }

        private static String sendRequest(URI uri) {
            var req = HttpRequest
                    .newBuilder(uri)
                    .GET()
                    .build();

            HttpClient client = HttpClient.newHttpClient();


            return client.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    //.thenAccept()
                    .join();
        }

        private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {
            OutputStream outputStream = httpExchange.getResponseBody();
            String result = "";

            URI uri = algo.getNextAvailableServer().resolve(requestParamValue);
            result = sendRequest(uri);

            // this line is a must
            httpExchange.sendResponseHeaders(200, result.length());
            outputStream.write(result.getBytes());
            outputStream.flush();
            outputStream.close();
        }

        private void handleNotSupported(HttpExchange httpExchange) throws IOException {
            OutputStream outputStream = httpExchange.getResponseBody();
            String result = "Http method not supported: " + httpExchange.getRequestMethod();


            // this line is a must
            httpExchange.sendResponseHeaders(405, result.length());
            outputStream.write(result.getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }
}
