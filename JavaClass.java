
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class JavaClass {
    public static void main(String[] args) throws Exception {
	var s = HttpServer.create(new InetSocketAddress(8080), 0);

	s.createContext("/api/users", e -> {
	    System.out.println("HIT API: " + e.getRequestURI());
	    var json = "[{\"name\":\"pera\",\"surname\":\"peric\"},{\"name\":\"mika\",\"surname\":\"mikic\"}]";
	    var bytes = json.getBytes(StandardCharsets.UTF_8);
	    e.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
	    e.sendResponseHeaders(200, bytes.length);
	    e.getResponseBody().write(bytes);
	    e.close();
	});

	s.createContext("/main.js", e -> {
	    System.out.println("HIT MAINJS: " + e.getRequestURI());
	    var bytes = Files.readAllBytes(Path.of("main.js"));
	    e.getResponseHeaders().set("Content-Type", "application/javascript; charset=utf-8");
	    e.sendResponseHeaders(200, bytes.length);
	    e.getResponseBody().write(bytes);
	    e.close();String sa = "asd";
	    sa.describeConstable();
	});

	s.createContext("/style.css", e -> {
	    System.out.println("HIT CSS: " + e.getRequestURI());
	    var bytes = Files.readAllBytes(Path.of("style.css"));
	    e.getResponseHeaders().set("Content-Type", "text/css; charset=utf-8");
	    e.sendResponseHeaders(200, bytes.length);
	    e.getResponseBody().write(bytes);
	    e.close();
	});

	s.createContext("/", e -> {
	    System.out.println("HIT ROOT: " + e.getRequestURI());
	    var bytes = Files.readAllBytes(Path.of("index.html"));
	    e.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
	    e.sendResponseHeaders(200, bytes.length);
	    e.getResponseBody().write(bytes);
	    e.close();
	});

	s.start();
	System.out.println("http://localhost:8080");
    }
}
