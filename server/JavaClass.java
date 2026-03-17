package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JavaClass {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

        List<Artist> artists = createArtists();
        List<Song> songs = createSongs();

        httpServer.createContext("/artists", exchange -> handleArtists(exchange, artists, songs));
        httpServer.createContext("/songs", exchange -> handleSongs(exchange, songs));
        httpServer.createContext("/views/main.js",
                exchange -> serveFile(exchange, "views/main.js", "application/javascript; charset=utf-8"));
        httpServer.createContext("/views/style.css",
                exchange -> serveFile(exchange, "views/style.css", "text/css; charset=utf-8"));
        httpServer.createContext("/",
                exchange -> serveFile(exchange, "views/index.html", "text/html; charset=utf-8"));

        httpServer.start();
        System.out.println("http://localhost:8080");
    }

    private static List<Artist> createArtists() {
        Artist artist1 = new Artist();
        artist1.setId(0);
        artist1.setName("pera");
        artist1.setSurname("peric");

        Artist artist2 = new Artist();
        artist2.setId(1);
        artist2.setName("mika");
        artist2.setSurname("mikic");

        List<Artist> artists = new ArrayList<>();
        artists.add(artist1);
        artists.add(artist2);
        return artists;
    }

    private static List<Song> createSongs() {
        List<Song> songs = new ArrayList<>();
        songs.add(new Song("Nije sve tako sivo", "Nije sve tako sivo\n kad imas s nekim otic na pivo", 0));
        songs.add(new Song("title2", "lyrics2", 1));
        return songs;
    }

    private static void handleArtists(HttpExchange exchange, List<Artist> artists, List<Song> songs) throws IOException {
        String path = exchange.getRequestURI().getPath();
        System.out.println("HIT ARTISTS: " + path);

        if (path.equals("/artists") || path.equals("/artists/")) {
            sendJson(exchange, artists);
            return;
        }

        if (path.matches("/artists/\\d+/songs/?")) {
            int artistId = extractArtistId(path);

            List<Song> filteredSongs = songs.stream()
                    .filter(song -> song.getArtistId() == artistId)
                    .toList();

            sendJson(exchange, filteredSongs);
            return;
        }

        sendNotFound(exchange);
    }

    private static void handleSongs(HttpExchange exchange, List<Song> songs) throws IOException {
        String path = exchange.getRequestURI().getPath();
        System.out.println("HIT SONGS: " + path);

        if (path.equals("/songs") || path.equals("/songs/")) {
            sendJson(exchange, songs);
            return;
        }

        sendNotFound(exchange);
    }

    private static int extractArtistId(String path) {
        String[] parts = path.split("/");
        return Integer.parseInt(parts[2]);
    }

    private static void sendJson(HttpExchange exchange, Object body) throws IOException {
        String responseJson = MAPPER.writeValueAsString(body);
        byte[] bytes = responseJson.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    private static void serveFile(HttpExchange exchange, String filePath, String contentType) throws IOException {
        System.out.println("HIT FILE: " + exchange.getRequestURI());

        byte[] bytes = Files.readAllBytes(Path.of(filePath));
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    private static void sendNotFound(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(404, -1);
        exchange.close();
    }
}