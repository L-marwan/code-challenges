package com.marouane.challenges.loadbalancer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleHttpServer {

    public static void main(String[] args) throws IOException {
        var server1 = getServer("localhost", 8080, "/");
        var server2 = getServer("localhost", 8081, "/test");

        server1.start();
        server2.start();


        System.out.println("Server started on port 8080...");
    }

    public static HttpServer getServer(String hostname, int port, String path) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(hostname, port), 0);

        ExecutorService threadPoolExecutor = Executors.newVirtualThreadPerTaskExecutor();

        server.createContext(path, new MyHttpHandler("server:" + port));
        server.setExecutor(threadPoolExecutor);
        return server;
    }

    static class MyHttpHandler implements HttpHandler {

        private String serverName;

        public MyHttpHandler(String serverName) {
            this.serverName = serverName;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            //System.out.println("handle request on thread: " + Thread.currentThread());

            String requestParamValue = null;
            if ("GET".equals(httpExchange.getRequestMethod())) {
                requestParamValue = serverName;
            } else {
                requestParamValue = "http method not supported";
            }
            handleResponse(httpExchange, requestParamValue);
        }

        private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {
            OutputStream outputStream = httpExchange.getResponseBody();
            String result = "Hello From %s".formatted(requestParamValue);

            // this line is a must
            httpExchange.sendResponseHeaders(200, result.length());
            outputStream.write(result.getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }
}
