package server.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class HttpResponses {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private HttpResponses() {
    }

    public static void sendJson(HttpExchange exchange, Object body) throws IOException {
        sendJson(exchange, 200, body);
    }

    public static void sendJson(HttpExchange exchange, int statusCode, Object body) throws IOException {
        String responseJson = MAPPER.writeValueAsString(body);
        byte[] bytes = responseJson.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    public static void sendBadRequest(HttpExchange exchange, String message) throws IOException {
        sendJson(exchange, 400, Map.of("error", message));
    }

    public static void sendNotFound(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(404, -1);
        exchange.close();
    }

    public static void sendMethodNotAllowed(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(405, -1);
        exchange.close();
    }
}