package com.example.dbdesign_project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 사용자 추가 메서드
    public int addUser(String userName, String password) {
        String sql = "INSERT INTO User (userName, password) VALUES (?, ?)";
        return jdbcTemplate.update(sql, userName, password);
    }

    // ID로 사용자 조회 메서드
    public User findUserById(int userId) {
        String sql = "SELECT * FROM User WHERE userId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, new UserRowMapper());
    }

    // 사용자 이름으로 사용자 조회 메서드 (중복 체크용)
    public User findUserByName(String userName) {
        String sql = "SELECT * FROM User WHERE userName = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userName}, new UserRowMapper());
    }

    // User 엔티티로 매핑하기 위한 RowMapper
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("userId"));
            user.setUserName(rs.getString("userName"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
}
