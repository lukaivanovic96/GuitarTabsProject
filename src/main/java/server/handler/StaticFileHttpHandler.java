
package server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class StaticFileHttpHandler implements HttpHandler {

    private final String filePath;
    private final String contentType;

    public StaticFileHttpHandler(String filePath, String contentType) {
        this.filePath = filePath;
        this.contentType = contentType;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        StaticFileHandler.serveFile(exchange, filePath, contentType);
    }
}