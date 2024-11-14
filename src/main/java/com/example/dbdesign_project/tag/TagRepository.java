package com.example.dbdesign_project.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TagRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 태그 추가 메서드
    public int addTag(String tagName) {
        String sql = "INSERT INTO Tag (tagName) VALUES (?)";
        return jdbcTemplate.update(sql, tagName);
    }

    // 모든 태그 조회 메서드
    public List<Tag> findAllTags() {
        String sql = "SELECT * FROM Tag";
        return jdbcTemplate.query(sql, new TagRowMapper());
    }

    // 태그 ID로 특정 태그 조회
    public Tag findTagById(int tagId) {
        String sql = "SELECT * FROM Tag WHERE tagId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{tagId}, new TagRowMapper());
    }

    // 태그 이름으로 특정 태그 조회 (중복 확인 등)
    public Tag findTagByName(String tagName) {
        String sql = "SELECT * FROM Tag WHERE tagName = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{tagName}, new TagRowMapper());
    }

    // 태그 삭제 메서드
    public int deleteTag(int tagId) {
        String sql = "DELETE FROM Tag WHERE tagId = ?";
        return jdbcTemplate.update(sql, tagId);
    }

    // Tag 엔티티로 매핑하기 위한 RowMapper
    private static class TagRowMapper implements RowMapper<Tag> {
        @Override
        public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
            Tag tag = new Tag();
            tag.setTagId(rs.getInt("tagId"));
            tag.setTagName(rs.getString("tagName"));
            return tag;
        }
    }
}
