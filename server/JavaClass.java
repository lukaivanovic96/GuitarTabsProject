package server;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class JavaClass {
    public static void main(String[] args) throws Exception {
	HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

	httpServer.createContext("/artists", e -> {
	    System.out.println("HIT API: " + e.getRequestURI());
	    String json = """
		[
			{
				"id"		: 0,
				"name"		: "pera",
				"surname"	: "peric"
			},
			{
				"id"		: 1,
				"name"		: "mika",
				"surname"	: "mikic"
			}
		]
		""";
	    var bytes = json.getBytes(StandardCharsets.UTF_8);
	    e.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
	    e.sendResponseHeaders(200, bytes.length);
	    e.getResponseBody().write(bytes);
	    e.close();
	});

	httpServer.createContext("/songs", e -> {
	    System.out.println("HIT API: " + e.getRequestURI());
	    String json = """
		[
			{
				"artist_id"	: 0,
				"song_id" 	: 0,
				"title" 	: "Pesma 1",
				"lyrics"	: "Tekst Pesme 1"
			},
			{
				"artist_id"	: 0,
				"song_id" 	: 1,
				"title"		: "Pesma 2",
				"lyrics"	: "Tekst Pesme 2"
			},
			{
				"artist_id"	: 1,
				"song_id" 	: 2,
				"title"		: "Pesma 3",
				"lyrics"	: "Tekst Pesme 3"
			}
		]
		""";
	    var bytes = json.getBytes(StandardCharsets.UTF_8);
	    e.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
	    e.sendResponseHeaders(200, bytes.length);
	    e.getResponseBody().write(bytes);
	    e.close();
	});


	httpServer.createContext("/views/main.js", e -> {
	    System.out.println("HIT MAINJS: " + e.getRequestURI());
	    var bytes = Files.readAllBytes(Path.of("views/main.js"));
	    e.getResponseHeaders().set("Content-Type", "application/javascript; charset=utf-8");
	    e.sendResponseHeaders(200, bytes.length);
	    e.getResponseBody().write(bytes);
	    e.close();
	});

	httpServer.createContext("/views/style.css", e -> {
	    System.out.println("HIT CSS: " + e.getRequestURI());
	    var bytes = Files.readAllBytes(Path.of("views/style.css"));
	    e.getResponseHeaders().set("Content-Type", "text/css; charset=utf-8");
	    e.sendResponseHeaders(200, bytes.length);
	    e.getResponseBody().write(bytes);
	    e.close();
	});

	httpServer.createContext("/", e -> {
	    System.out.println("HIT ROOT: " + e.getRequestURI());
	    var bytes = Files.readAllBytes(Path.of("views/index.html"));
	    e.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
	    e.sendResponseHeaders(200, bytes.length);
	    e.getResponseBody().write(bytes);
	    e.close();
	});

	httpServer.start();
	System.out.println("http://localhost:8080");
    }
}
