package server.repository.impl;

import server.model.Song;
import server.repository.SongRepository;

import java.util.List;

public class SongRepositoryImpl implements SongRepository {

    private final List<Song> songs;

    public SongRepositoryImpl(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public List<Song> findAll() {
        return songs;
    }

    @Override
    public List<Song> findByArtistId(int artistId) {
        return songs.stream()
                .filter(song -> song.getArtistId() == artistId)
                .toList();
    }
}