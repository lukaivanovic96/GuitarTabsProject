package server.repository.impl;

import server.model.Artist;
import server.repository.ArtistRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ArtistRepositoryJdbc implements ArtistRepository {

    private final Connection connection;

    public ArtistRepositoryJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Artist> findAll() {
        List<Artist> artists = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT id, name, surname FROM artists");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Artist artist = Artist.builder().build();
                artist.setId(resultSet.getInt("id"));
                artist.setName(resultSet.getString("name"));
                artist.setSurname(resultSet.getString("surname"));
                artists.add(artist);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return artists;
    }

    @Override
    public Artist save(Artist artist) {
        return null;
    }
}