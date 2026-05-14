package server.repository.impl;

import server.model.Song;
import server.repository.DatabaseConnection;
import server.repository.SongRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongRepositoryJdbc implements SongRepository {

    @Override
    public List<Song> findAll() {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT id, title, lyrics, artist_id FROM songs ORDER BY id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                songs.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch songs", e);
        }

        return songs;
    }

    @Override
    public List<Song> findByArtistId(int artistId) {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT id, title, lyrics, artist_id FROM songs WHERE artist_id = ? ORDER BY id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, artistId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    songs.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch songs by artist", e);
        }

        return songs;
    }

    @Override
    public Song save(Song song) {
        String sql = "INSERT INTO songs(title, lyrics, artist_id) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, song.getTitle());
            statement.setString(2, song.getLyrics());
            statement.setInt(3, song.getArtistId());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return Song.builder()
                            .id(generatedKeys.getInt(1))
                            .title(song.getTitle())
                            .lyrics(song.getLyrics())
                            .artistId(song.getArtistId())
                            .build();
                }
            }

            throw new RuntimeException("Song was saved, but no ID was returned");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save song", e);
        }
    }

    @Override
    public Song update(int id, Song song) {
        String sql = "UPDATE songs SET title = ?, lyrics = ?, artist_id = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, song.getTitle());
            statement.setString(2, song.getLyrics());
            statement.setInt(3, song.getArtistId());
            statement.setInt(4, id);

            int updatedRows = statement.executeUpdate();

            if (updatedRows == 0) {
                return null;
            }

            return Song.builder()
                    .id(id)
                    .title(song.getTitle())
                    .lyrics(song.getLyrics())
                    .artistId(song.getArtistId())
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update song", e);
        }
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM songs WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            int deletedRows = statement.executeUpdate();
            return deletedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete song", e);
        }
    }

    private Song mapRow(ResultSet rs) throws SQLException {
        return Song.builder()
                .id(rs.getInt("id"))
                .title(rs.getString("title"))
                .lyrics(rs.getString("lyrics"))
                .artistId(rs.getInt("artist_id"))
                .build();
    }
}
