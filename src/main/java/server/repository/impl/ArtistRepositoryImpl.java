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
}