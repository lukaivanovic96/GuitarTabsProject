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

        httpServer.createContext("/main.js",
                new StaticFileHttpHandler("main.js", "application/javascript; charset=utf-8"));
        httpServer.createContext("/style.css",
                new StaticFileHttpHandler("style.css", "text/css; charset=utf-8"));
        httpServer.createContext("/",
                new StaticFileHttpHandler("index.html", "text/html; charset=utf-8"));

        httpServer.start();
        logger.info("http://localhost:8080");
    }
}