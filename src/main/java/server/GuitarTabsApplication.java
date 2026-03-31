package server;

import com.sun.net.httpserver.HttpServer;
import server.handler.ArtistHandler;
import server.handler.SongHandler;
import server.handler.StaticFileHttpHandler;
import server.model.Artist;
import server.model.Song;
import server.repository.ArtistRepository;
import server.repository.impl.ArtistRepositoryImpl;
import server.repository.SongRepository;
import server.repository.impl.SongRepositoryImpl;
import server.bootstrap.SampleData;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.logging.Logger;

public class GuitarTabsApplication {

    private static final Logger logger = Logger.getLogger(GuitarTabsApplication.class.getName());

    public static void main(String[] args) throws Exception {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

        List<Artist> artists = SampleData.createArtists();
        List<Song> songs = SampleData.createSongs();

        ArtistRepository artistRepository = new ArtistRepositoryImpl(artists);
        SongRepository songRepository = new SongRepositoryImpl(songs);

        httpServer.createContext("/artists", new ArtistHandler(artistRepository, songRepository));
        httpServer.createContext("/songs", new SongHandler(songRepository));

        httpServer.createContext("/js", exchange -> {
                String path = exchange.getRequestURI().getPath(); // npr. /js/main.js
                String filePath = "src/main/resources" + path; // ili "resources" folder: "./resources" + path
                String mime = "application/javascript; charset=utf-8";

                try {
                        byte[] bytes = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath));
                        exchange.getResponseHeaders().add("Content-Type", mime);
                        exchange.sendResponseHeaders(200, bytes.length);
                        exchange.getResponseBody().write(bytes);
                } catch (Exception e) {
                        // fajl ne postoji → 404
                        exchange.sendResponseHeaders(404, -1);
                } finally {
                        exchange.close();
                }
        });
        httpServer.createContext("/style.css",
                new StaticFileHttpHandler("style.css", "text/css; charset=utf-8"));
        httpServer.createContext("/",
                new StaticFileHttpHandler("index.html", "text/html; charset=utf-8"));
        httpServer.createContext("/artists.html",
                new StaticFileHttpHandler("artists.html", "text/html; charset=utf-8"));
        httpServer.createContext("/songs.html",
                new StaticFileHttpHandler("songs.html", "text/html; charset=utf-8"));
        httpServer.createContext("/about_us.html",
                new StaticFileHttpHandler("about_us.html", "text/html; charset=utf-8"));


        httpServer.createContext("/images/guitar_tabs_logo_4.png",
                new StaticFileHttpHandler("images/guitar_tabs_logo_4.png", "image/png"));

        httpServer.start();
        logger.info("http://localhost:8080");
    }
}