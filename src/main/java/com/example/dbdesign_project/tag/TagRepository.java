package com.example.dbdesign_project.tag;

import com.example.dbdesign_project.tag.Tag;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TagRepository {
    private final JdbcTemplate jdbcTemplate;

    public TagRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Tag tag) {
        String sql = "INSERT INTO Tag (tagName) VALUES (?)";
        return jdbcTemplate.update(sql, tag.getTagName());
    }

    public Optional<Tag> findById(Integer tagId) {
        String sql = "SELECT * FROM Tag WHERE tagId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Tag.class), tagId)
                .stream().findFirst();
    }

    public Optional<Tag> findByName(String tagName) {
        String sql = "SELECT * FROM Tag WHERE tagName = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Tag.class), tagName)
                .stream().findFirst();
    }

    public int deleteById(Integer tagId) {
        String sql = "DELETE FROM Tag WHERE tagId = ?";
        return jdbcTemplate.update(sql, tagId);
    }
}
