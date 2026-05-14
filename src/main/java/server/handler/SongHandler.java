package server.handler;

import com.sun.net.httpserver.HttpExchange;
import server.model.Song;
import server.repository.SongRepository;
import server.utils.HttpResponses;
import server.utils.Json;

import java.io.IOException;
import java.util.logging.Logger;

public class SongHandler extends BaseHandler {

    private static final Logger logger = Logger.getLogger(SongHandler.class.getName());

    private final SongRepository songRepository;

    public SongHandler(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    protected void handleGet(HttpExchange exchange, String path) throws IOException {
        logger.info("GET " + path);

        if (path.equals("/songs") || path.equals("/songs/")) {
            HttpResponses.sendJson(exchange, songRepository.findAll());
            return;
        }

        HttpResponses.sendNotFound(exchange);
    }

    @Override
    protected void handlePost(HttpExchange exchange, String path) throws IOException {
        logger.info("POST " + path);

        if (!path.equals("/songs") && !path.equals("/songs/")) {
            HttpResponses.sendNotFound(exchange);
            return;
        }

        try {
            SongDto request = Json.MAPPER.readValue(exchange.getRequestBody(), SongDto.class);
            validateSongDto(request);
            Song savedSong = songRepository.save(toSong(request));
            HttpResponses.sendJson(exchange, 201, savedSong);
        } catch (IOException e) {
            HttpResponses.sendBadRequest(exchange, "Invalid JSON body");
        } catch (IllegalArgumentException e) {
            HttpResponses.sendBadRequest(exchange, e.getMessage());
        }
    }

    @Override
    protected void handlePut(HttpExchange exchange, String path) throws IOException {
        logger.info("PUT " + path);

        if (!path.matches("/songs/\\d+/?")) {
            HttpResponses.sendNotFound(exchange);
            return;
        }

        int songId = extractSongId(path);

        try {
            SongDto request = Json.MAPPER.readValue(exchange.getRequestBody(), SongDto.class);
            validateSongDto(request);
            Song updatedSong = songRepository.update(songId, toSong(request));

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

    @Override
    protected void handleDelete(HttpExchange exchange, String path) throws IOException {
        logger.info("DELETE " + path);

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
