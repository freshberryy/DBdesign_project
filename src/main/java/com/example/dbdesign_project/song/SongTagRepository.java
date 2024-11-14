package com.example.dbdesign_project.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SongTagRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SongTagRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 노래에 태그 추가
    public int addTagToSong(int songId, int tagId) {
        String sql = "INSERT INTO SongTag (songId, tagId) VALUES (?, ?)";
        return jdbcTemplate.update(sql, songId, tagId);
    }

    // 노래에서 태그 제거
    public int removeTagFromSong(int songId, int tagId) {
        String sql = "DELETE FROM SongTag WHERE songId = ? AND tagId = ?";
        return jdbcTemplate.update(sql, songId, tagId);
    }

    // 특정 노래에 연결된 모든 태그 조회
    public List<Integer> findTagsBySongId(int songId) {
        String sql = "SELECT tagId FROM SongTag WHERE songId = ?";
        return jdbcTemplate.query(sql, new Object[]{songId}, (rs, rowNum) -> rs.getInt("tagId"));
    }

    // 특정 태그에 연결된 모든 노래 조회
    public List<Integer> findSongsByTagId(int tagId) {
        String sql = "SELECT songId FROM SongTag WHERE tagId = ?";
        return jdbcTemplate.query(sql, new Object[]{tagId}, (rs, rowNum) -> rs.getInt("songId"));
    }
}
