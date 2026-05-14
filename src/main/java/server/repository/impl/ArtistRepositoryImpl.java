package server.repository.impl;

import server.model.Artist;
import server.repository.ArtistRepository;

import java.util.ArrayList;
import java.util.List;

public class ArtistRepositoryImpl implements ArtistRepository {

    private final List<Artist> artists;

    public ArtistRepositoryImpl(List<Artist> artists) {
        this.artists = new ArrayList<>(artists);
    }

    @Override
    public List<Artist> findAll() {
        return artists;
    }

    @Override
    public Artist save(Artist artist) {
        int nextId = artists.stream()
                .mapToInt(Artist::getId)
                .max()
                .orElse(-1) + 1;

        artist.setId(nextId);
        artists.add(artist);
        return artist;
    }

    @Override
    public Artist update(int id, Artist updated) {
        for (Artist artist : artists) {
            if (artist.getId() == id) {
                artist.setName(updated.getName());
                artist.setSurname(updated.getSurname());
                return artist;
            }
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return artists.removeIf(artist -> artist.getId() == id);
    }
}