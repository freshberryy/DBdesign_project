package com.example.dbdesign_project.playlist;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlaylistSongRepository {
    private final JdbcTemplate jdbcTemplate;

    public PlaylistSongRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addSongToPlaylist(Integer listId, Integer songId) {
        String sql = "INSERT INTO PlaylistSong (listId, songId) VALUES (?, ?)";
        return jdbcTemplate.update(sql, listId, songId);
    }

    public int removeSongFromPlaylist(Integer listId, Integer songId) {
        String sql = "DELETE FROM PlaylistSong WHERE listId = ? AND songId = ?";
        return jdbcTemplate.update(sql, listId, songId);
    }
}
