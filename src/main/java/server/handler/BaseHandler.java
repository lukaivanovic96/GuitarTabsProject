package server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.utils.HttpResponses;

import java.io.IOException;

public abstract class BaseHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        switch (method.toUpperCase()) {
            case "GET"    -> handleGet(exchange, path);
            case "POST"   -> handlePost(exchange, path);
            case "PUT"    -> handlePut(exchange, path);
            case "DELETE" -> handleDelete(exchange, path);
            default       -> HttpResponses.sendMethodNotAllowed(exchange);
        }
    }

    protected void handleGet(HttpExchange exchange, String path) throws IOException {
        HttpResponses.sendMethodNotAllowed(exchange);
    }

    protected void handlePost(HttpExchange exchange, String path) throws IOException {
        HttpResponses.sendMethodNotAllowed(exchange);
    }

    protected void handlePut(HttpExchange exchange, String path) throws IOException {
        HttpResponses.sendMethodNotAllowed(exchange);
    }

    protected void handleDelete(HttpExchange exchange, String path) throws IOException {
        HttpResponses.sendMethodNotAllowed(exchange);
    }
}
