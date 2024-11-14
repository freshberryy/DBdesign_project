package com.example.dbdesign_project.playlist;

import com.example.dbdesign_project.playlist.Playlist;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PlaylistRepository {
    private final JdbcTemplate jdbcTemplate;

    public PlaylistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Playlist playlist) {
        String sql = "INSERT INTO Playlist (userId, listName, creationTime) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, playlist.getUserId(), playlist.getListName(), playlist.getCreationTime());
    }

    public Optional<Playlist> findById(Integer listId) {
        String sql = "SELECT * FROM Playlist WHERE listId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Playlist.class), listId)
                .stream().findFirst();
    }

    public List<Playlist> findByUserId(Integer userId) {
        String sql = "SELECT * FROM Playlist WHERE userId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Playlist.class), userId);
    }

    public int deleteById(Integer listId) {
        String sql = "DELETE FROM Playlist WHERE listId = ?";
        return jdbcTemplate.update(sql, listId);
    }
}
