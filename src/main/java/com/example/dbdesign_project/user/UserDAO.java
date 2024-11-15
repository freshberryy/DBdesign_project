package com.example.dbdesign_project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int registerUser(String username, String password) {
        String sql = "insert into User (username, password) values (?, ?)";
        return jdbcTemplate.update(sql, username, password);
    }

    public boolean loginUser(String username, String password) {
        String sql = "select count(*) from User where username=? and password=?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
        return count != null && count > 0;
    }
}
