package server.bootstrap;

import server.model.Artist;
import server.model.Song;

import java.util.List;

public class SampleData {

    public static List<Artist> createArtists() {
        Artist artist1 = Artist.builder().id(0).name("pera").surname("peric").build();
        Artist artist2 = Artist.builder().id(1).name("mika").surname("mikic").build();

        return List.of(artist1, artist2);
    }

    public static List<Song> createSongs() {
        Song song1 = Song.builder().title("Nije sve tako sivo")
                .lyrics("Nije sve tako sivo\n kad imas s nekim otic na pivo").artistId(0).build();
        Song song2 = Song.builder().title("title2").lyrics("lyrics2").artistId(1).build();

        return List.of(song1, song2);
    }
}