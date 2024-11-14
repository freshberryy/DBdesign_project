package com.example.dbdesign_project.song;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SongTagRepository {
    private final JdbcTemplate jdbcTemplate;

    public SongTagRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addTagToSong(Integer songId, Integer tagId) {
        String sql = "INSERT INTO SongTag (songId, tagId) VALUES (?, ?)";
        return jdbcTemplate.update(sql, songId, tagId);
    }

    public int removeTagFromSong(Integer songId, Integer tagId) {
        String sql = "DELETE FROM SongTag WHERE songId = ? AND tagId = ?";
        return jdbcTemplate.update(sql, songId, tagId);
    }
}
