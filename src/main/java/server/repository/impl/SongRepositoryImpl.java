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

    @Override
    public Song save(Song song) {
        int nextId = songs.stream()
                .mapToInt(Song::getId)
                .max()
                .orElse(-1) + 1;

        song.setId(nextId);
        songs.add(song);
        return song;
    }

    @Override
    public Song update(int id, Song updated) {
        for (Song song : songs) {
            if (song.getId() == id) {
                song.setTitle(updated.getTitle());
                song.setLyrics(updated.getLyrics());
                song.setArtistId(updated.getArtistId());
                return song;
            }
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return songs.removeIf(song -> song.getId() == id);
    }
}