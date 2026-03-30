package server.repository;

import server.model.Song;

import java.util.List;

public interface SongRepository {
    List<Song> findAll();
    List<Song> findByArtistId(int artistId);
}