package server.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Song class represents a song with its title, lyrics, and the ID of the artist who created it.
 */
@Getter
@Setter
@Builder
public class Song {
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
}
