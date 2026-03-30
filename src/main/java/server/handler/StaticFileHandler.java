package server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;

public final class StaticFileHandler {

    public static void serveFile(HttpExchange exchange, String resourcePath, String contentType) throws IOException {

        try (InputStream is = StaticFileHandler.class
                .getClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (is == null) {
                exchange.sendResponseHeaders(404, -1);
                exchange.close();
                return;
            }

            byte[] bytes = is.readAllBytes();

            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
        } finally {
            exchange.close();
        }
    }
}