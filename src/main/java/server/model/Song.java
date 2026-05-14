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
    private int id;
    private String title;
    private String lyrics;
    private int artistId;
}
