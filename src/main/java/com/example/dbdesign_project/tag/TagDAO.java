package com.example.dbdesign_project.tag;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDAO {
    private final JdbcTemplate jdbcTemplate;

    public TagDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 태그 추가
    public void addTag(String tagName) {
        String sql = "INSERT IGNORE INTO Tag (tagName) VALUES (?)";
        jdbcTemplate.update(sql, tagName);
    }

    // 모든 태그 조회
    public List<String> getAllTags() {
        String sql = "SELECT tagName FROM Tag";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    // 태그 삭제
    public void deleteTag(String tagName) {
        String sql = "DELETE FROM Tag WHERE tagName = ?";
        jdbcTemplate.update(sql, tagName);
    }

    // 특정 태그의 ID 조회
    public Integer getTagId(String tagName) {
        String sql = "SELECT tagId FROM Tag WHERE tagName = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{tagName}, Integer.class);
    }
}
