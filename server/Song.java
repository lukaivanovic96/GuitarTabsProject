package server;

/**
 * Song class represents a song with its title, lyrics, and the ID of the artist who created it.
 */
public class Song {
    public Song(String title, String lyrics, int artistId) {
        this.title = title;
        this.lyrics = lyrics;
        this.artistId = artistId;
    }

    /*
     * The title of the song.
     */
    private String title;
    /**
     * The lyrics of the song.
     */
    private String lyrics;
    /*
     * The ID of the artist who created the song.
     */
    private int artistId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }
}
