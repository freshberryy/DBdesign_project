package com.example.dbdesign_project.user;

import com.example.dbdesign_project.user.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(User user) {
        String sql = "INSERT INTO User (userName, password) VALUES (?, ?)";
        return jdbcTemplate.update(sql, user.getUserName(), user.getPassword());
    }

    public Optional<User> findById(Integer userId) {
        String sql = "SELECT * FROM User WHERE userId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), userId)
                .stream().findFirst();
    }

    public Optional<User> findByUsername(String userName) {
        String sql = "SELECT * FROM User WHERE userName = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), userName)
                .stream().findFirst();
    }

    public int deleteById(Integer userId) {
        String sql = "DELETE FROM User WHERE userId = ?";
        return jdbcTemplate.update(sql, userId);
    }
}
