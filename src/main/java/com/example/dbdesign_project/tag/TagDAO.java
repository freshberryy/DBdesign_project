package com.example.dbdesign_project.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    // 태그 추가
    // 노래에 태그 추가
    public void addTagToSong(int songId, String tagName) {
        String insertTag = "INSERT IGNORE INTO Tag (tagName) VALUES (?)";
        jdbcTemplate.update(insertTag, tagName);
        String getTagId = "SELECT tagId FROM Tag WHERE tagName = ?";
        Integer tagId = jdbcTemplate.queryForObject(getTagId, new Object[]{tagName}, Integer.class);
        String linkTag = "INSERT INTO SongTag (songId, tagId) VALUES (?, ?)";
        jdbcTemplate.update(linkTag, songId, tagId);
    }

    // 모든 태그 조회
    public List<String> getTagsForSong(int songId) {
        String sql = "SELECT t.tagName FROM Tag t " +
                "JOIN SongTag st ON t.tagId = st.tagId " +
                "WHERE st.songId = ?";
        return jdbcTemplate.queryForList(sql, new Object[]{songId}, String.class);
    }

    // 태그 삭제
    public void removeTagFromSong(int songId, String tagName) {
        String getTagId = "SELECT tagId FROM Tag WHERE tagName = ?";
        Integer tagId = jdbcTemplate.queryForObject(getTagId, new Object[]{tagName}, Integer.class);
        String unlinkTag = "DELETE FROM SongTag WHERE songId = ? AND tagId = ?";
        jdbcTemplate.update(unlinkTag, songId, tagId);
    }
}
