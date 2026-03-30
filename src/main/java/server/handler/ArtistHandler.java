package server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.model.Artist;
import server.repository.ArtistRepository;
import server.repository.SongRepository;
import server.utils.HttpResponses;

import java.io.IOException;
import java.util.logging.Logger;

public class ArtistHandler implements HttpHandler {

    private static final Logger logger = Logger.getLogger(ArtistHandler.class.getName());
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    public ArtistHandler(ArtistRepository artistRepository, SongRepository songRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        logger.info("HIT ARTISTS: " + method + " " + path);

        if ("GET".equalsIgnoreCase(method)) {
            handleGet(exchange, path);
            return;
        }

        if ("POST".equalsIgnoreCase(method)) {
            handlePost(exchange, path);
            return;
        }

        HttpResponses.sendMethodNotAllowed(exchange);
    }

    private void handleGet(HttpExchange exchange, String path) throws IOException {
        if (path.equals("/artists") || path.equals("/artists/")) {
            HttpResponses.sendJson(exchange, artistRepository.findAll());
            return;
        }

        if (path.matches("/artists/\\d+/songs/?")) {
            int artistId = extractArtistId(path);
            HttpResponses.sendJson(exchange, songRepository.findByArtistId(artistId));
            return;
        }

        HttpResponses.sendNotFound(exchange);
    }

    private void handlePost(HttpExchange exchange, String path) throws IOException {
        if (!path.equals("/artists") && !path.equals("/artists/")) {
            HttpResponses.sendNotFound(exchange);
            return;
        }

        try {
            ArtistDto request = MAPPER.readValue(exchange.getRequestBody(), ArtistDto.class);

            if (request.getName() == null || request.getName().isBlank()) {
                HttpResponses.sendBadRequest(exchange, "Artist name is required");
                return;
            }

            if (request.getSurname() == null || request.getSurname().isBlank()) {
                HttpResponses.sendBadRequest(exchange, "Artist surname is required");
                return;
            }

            Artist artist = Artist.builder()
                    .name(request.getName().trim())
                    .surname(request.getSurname().trim())
                    .build();

            Artist savedArtist = artistRepository.save(artist);
            HttpResponses.sendJson(exchange, 201, savedArtist);

        } catch (Exception e) {
            HttpResponses.sendBadRequest(exchange, "Invalid JSON body");
        }
    }

    private int extractArtistId(String path) {
        String[] parts = path.split("/");
        return Integer.parseInt(parts[2]);
    }
}