package server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.repository.SongRepository;
import server.utils.HttpResponses;

import java.io.IOException;
import java.util.logging.Logger;

public class SongHandler implements HttpHandler {

    private static final Logger logger = Logger.getLogger(SongHandler.class.getName());

    private final SongRepository songRepository;

    public SongHandler(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        logger.info("HIT SONGS: " + path);

        if (path.equals("/songs") || path.equals("/songs/")) {
            HttpResponses.sendJson(exchange, songRepository.findAll());
        } else {
            HttpResponses.sendNotFound(exchange);
        }

    }
}