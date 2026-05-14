package server.repository;

import server.model.Artist;

import java.util.List;

public interface ArtistRepository {
    List<Artist> findAll();
    Artist save(Artist artist);
    Artist update(int id, Artist artist);
    boolean deleteById(int id);
}