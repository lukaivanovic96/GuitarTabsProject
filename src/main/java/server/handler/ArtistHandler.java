package server.handler;

import com.sun.net.httpserver.HttpExchange;
import server.model.Artist;
import server.repository.ArtistRepository;
import server.repository.SongRepository;
import server.utils.HttpResponses;
import server.utils.Json;

import java.io.IOException;
import java.util.logging.Logger;

public class ArtistHandler extends BaseHandler {

    private static final Logger logger = Logger.getLogger(ArtistHandler.class.getName());

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    public ArtistHandler(ArtistRepository artistRepository, SongRepository songRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }

    @Override
    protected void handleGet(HttpExchange exchange, String path) throws IOException {
        logger.info("GET " + path);

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

    @Override
    protected void handlePost(HttpExchange exchange, String path) throws IOException {
        logger.info("POST " + path);

        if (!path.equals("/artists") && !path.equals("/artists/")) {
            HttpResponses.sendNotFound(exchange);
            return;
        }

        try {
            ArtistDto request = Json.MAPPER.readValue(exchange.getRequestBody(), ArtistDto.class);
            validateArtistDto(request);
            Artist savedArtist = artistRepository.save(toArtist(request));
            HttpResponses.sendJson(exchange, 201, savedArtist);
        } catch (IOException e) {
            HttpResponses.sendBadRequest(exchange, "Invalid JSON body");
        } catch (IllegalArgumentException e) {
            HttpResponses.sendBadRequest(exchange, e.getMessage());
        }
    }

    @Override
    protected void handlePut(HttpExchange exchange, String path) throws IOException {
        logger.info("PUT " + path);

        if (!path.matches("/artists/\\d+/?")) {
            HttpResponses.sendNotFound(exchange);
            return;
        }

        int artistId = extractArtistId(path);

        try {
            ArtistDto request = Json.MAPPER.readValue(exchange.getRequestBody(), ArtistDto.class);
            validateArtistDto(request);
            Artist updatedArtist = artistRepository.update(artistId, toArtist(request));

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

    @Override
    protected void handleDelete(HttpExchange exchange, String path) throws IOException {
        logger.info("DELETE " + path);

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
