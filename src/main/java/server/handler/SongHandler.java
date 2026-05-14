package server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.model.Song;
import server.repository.SongRepository;
import server.utils.HttpResponses;
import server.utils.Json;

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
        String method = exchange.getRequestMethod();

        logger.info("HIT SONGS: " + method + " " + path);

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
        if (path.equals("/songs") || path.equals("/songs/")) {
            HttpResponses.sendJson(exchange, songRepository.findAll());
            return;
        }

        HttpResponses.sendNotFound(exchange);
    }

    private void handlePost(HttpExchange exchange, String path) throws IOException {
        if (!path.equals("/songs") && !path.equals("/songs/")) {
            HttpResponses.sendNotFound(exchange);
            return;
        }

        try {
            SongDto request = Json.MAPPER.readValue(exchange.getRequestBody(), SongDto.class);

            validateSongDto(request);

            Song song = toSong(request);
            Song savedSong = songRepository.save(song);

            HttpResponses.sendJson(exchange, 201, savedSong);

        } catch (IOException e) {
            HttpResponses.sendBadRequest(exchange, "Invalid JSON body");
        } catch (IllegalArgumentException e) {
            HttpResponses.sendBadRequest(exchange, e.getMessage());
        }
    }

    private void handlePut(HttpExchange exchange, String path) throws IOException {
        if (!path.matches("/songs/\\d+/?")) {
            HttpResponses.sendNotFound(exchange);
            return;
        }

        int songId = extractSongId(path);

        try {
            SongDto request = Json.MAPPER.readValue(exchange.getRequestBody(), SongDto.class);

            validateSongDto(request);

            Song song = toSong(request);
            Song updatedSong = songRepository.update(songId, song);

            if (updatedSong == null) {
                HttpResponses.sendNotFound(exchange);
                return;
            }

            HttpResponses.sendJson(exchange, updatedSong);

        } catch (IOException e) {
            HttpResponses.sendBadRequest(exchange, "Invalid JSON body");
        } catch (IllegalArgumentException e) {
            HttpResponses.sendBadRequest(exchange, e.getMessage());
        }
    }

    private void handleDelete(HttpExchange exchange, String path) throws IOException {
        if (!path.matches("/songs/\\d+/?")) {
            HttpResponses.sendNotFound(exchange);
            return;
        }

        int songId = extractSongId(path);
        boolean deleted = songRepository.deleteById(songId);

        if (!deleted) {
            HttpResponses.sendNotFound(exchange);
            return;
        }

        HttpResponses.sendNoContent(exchange);
    }

    private void validateSongDto(SongDto request) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new IllegalArgumentException("Song title is required");
        }

        if (request.getLyrics() == null || request.getLyrics().isBlank()) {
            throw new IllegalArgumentException("Song lyrics are required");
        }

        if (request.getArtistId() <= 0) {
            throw new IllegalArgumentException("Valid artist ID is required");
        }
    }

    private Song toSong(SongDto request) {
        return Song.builder()
                .title(request.getTitle().trim())
                .lyrics(request.getLyrics().trim())
                .artistId(request.getArtistId())
                .build();
    }

    private int extractSongId(String path) {
        String[] parts = path.split("/");
        return Integer.parseInt(parts[2]);
    }
}
