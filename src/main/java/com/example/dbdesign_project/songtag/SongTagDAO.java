package com.example.dbdesign_project.songtag;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SongTagDAO {
    private final JdbcTemplate jdbcTemplate;

    public SongTagDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 노래에 태그 추가
    public void addTagToSong(int songId, String tagName) {
        // 태그가 존재하지 않으면 추가
        String insertTag = "INSERT IGNORE INTO Tag (tagName) VALUES (?)";
        jdbcTemplate.update(insertTag, tagName);

        // 태그 ID 가져오기
        String getTagId = "SELECT tagId FROM Tag WHERE tagName = ?";
        Integer tagId = jdbcTemplate.queryForObject(getTagId, new Object[]{tagName}, Integer.class);

        // SongTag 연결 추가
        String linkTag = "INSERT INTO SongTag (songId, tagId) VALUES (?, ?)";
        jdbcTemplate.update(linkTag, songId, tagId);
    }

    // 노래에서 태그 삭제
    public void removeTagFromSong(int songId, String tagName) {
        // 태그 ID 가져오기
        String getTagId = "SELECT tagId FROM Tag WHERE tagName = ?";
        Integer tagId = jdbcTemplate.queryForObject(getTagId, new Object[]{tagName}, Integer.class);

        // SongTag 연결 삭제
        String unlinkTag = "DELETE FROM SongTag WHERE songId = ? AND tagId = ?";
        jdbcTemplate.update(unlinkTag, songId, tagId);
    }

    // 특정 노래의 태그 목록 조회
    public List<String> getTagsForSong(int songId) {
        String sql = "SELECT t.tagName FROM Tag t " +
                "JOIN SongTag st ON t.tagId = st.tagId " +
                "WHERE st.songId = ?";
        return jdbcTemplate.queryForList(sql, new Object[]{songId}, String.class);
    }
}
