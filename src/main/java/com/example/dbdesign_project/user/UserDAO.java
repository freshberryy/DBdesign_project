package com.example.dbdesign_project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 회원가입
    public int registerUser(String username, String password) {
        String sql = "insert into User (username, password) values (?, ?)";
        return jdbcTemplate.update(sql, username, password);
    }

    // 로그인
    public boolean loginUser(String username, String password) {
        String sql = "select count(*) from User where username=? and password=?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
        return count != null && count > 0;
    }

    // 사용자 이름으로 userId 가져오기
    public int getUserIdByUsername(String username) {
        String sql = "select userId from User where username = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, username);
    }
}

