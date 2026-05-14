package server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.model.Artist;
import server.repository.ArtistRepository;
import server.repository.SongRepository;
import server.utils.HttpResponses;
import server.utils.Json;

import java.io.IOException;
import java.util.logging.Logger;

public class ArtistHandler implements HttpHandler {

    private static final Logger logger = Logger.getLogger(ArtistHandler.class.getName());

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

        if ("PUT".equalsIgnoreCase(method)) {
            handlePut(exchange, path);
            return;
        }

        if ("DELETE".equalsIgnoreCase(method)) {
            handleDelete(exchange, path);
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
            ArtistDto request = Json.MAPPER.readValue(exchange.getRequestBody(), ArtistDto.class);

            validateArtistDto(request);

            Artist artist = toArtist(request);
            Artist savedArtist = artistRepository.save(artist);

            HttpResponses.sendJson(exchange, 201, savedArtist);

        } catch (IOException e) {
            HttpResponses.sendBadRequest(exchange, "Invalid JSON body");
        } catch (IllegalArgumentException e) {
            HttpResponses.sendBadRequest(exchange, e.getMessage());
        }
    }

    private void handlePut(HttpExchange exchange, String path) throws IOException {
        if (!path.matches("/artists/\\d+/?")) {
            HttpResponses.sendNotFound(exchange);
            return;
        }

        int artistId = extractArtistId(path);

        try {
            ArtistDto request = Json.MAPPER.readValue(exchange.getRequestBody(), ArtistDto.class);

            validateArtistDto(request);

            Artist artist = toArtist(request);
            Artist updatedArtist = artistRepository.update(artistId, artist);

            if (updatedArtist == null) {
                HttpResponses.sendNotFound(exchange);
                return;
            }

            HttpResponses.sendJson(exchange, updatedArtist);

        } catch (IOException e) {
            HttpResponses.sendBadRequest(exchange, "Invalid JSON body");
        } catch (IllegalArgumentException e) {
            HttpResponses.sendBadRequest(exchange, e.getMessage());
        }
    }

    private void handleDelete(HttpExchange exchange, String path) throws IOException {
        if (!path.matches("/artists/\\d+/?")) {
            HttpResponses.sendNotFound(exchange);
            return;
        }

        int artistId = extractArtistId(path);
        boolean deleted = artistRepository.deleteById(artistId);

        if (!deleted) {
            HttpResponses.sendNotFound(exchange);
            return;
        }

        HttpResponses.sendNoContent(exchange);
    }

    private void validateArtistDto(ArtistDto request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Artist name is required");
        }

        if (request.getSurname() == null || request.getSurname().isBlank()) {
            throw new IllegalArgumentException("Artist surname is required");
        }
    }

    private Artist toArtist(ArtistDto request) {
        return Artist.builder()
                .name(request.getName().trim())
                .surname(request.getSurname().trim())
                .build();
    }

    private int extractArtistId(String path) {
        String[] parts = path.split("/");
        return Integer.parseInt(parts[2]);
    }
}