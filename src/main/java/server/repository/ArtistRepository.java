package server.repository;

import server.model.Artist;

import java.util.List;

public interface ArtistRepository {
    List<Artist> findAll();
    Artist save(Artist artist);
}