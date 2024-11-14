package com.example.dbdesign_project.playlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PlaylistRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PlaylistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 재생목록 추가
    public int addPlaylist(int userId, String listName) {
        String sql = "INSERT INTO Playlist (userId, listName, creationTime) VALUES (?, ?, NOW())";
        return jdbcTemplate.update(sql, userId, listName);
    }

    // 특정 사용자의 모든 재생목록 조회
    public List<Playlist> findPlaylistsByUserId(int userId) {
        String sql = "SELECT * FROM Playlist WHERE userId = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, new PlaylistRowMapper());
    }

    // 특정 재생목록 조회 (listId 기준)
    public Playlist findPlaylistById(int listId) {
        String sql = "SELECT * FROM Playlist WHERE listId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{listId}, new PlaylistRowMapper());
    }

    // 재생목록 이름 수정
    public int updatePlaylistName(int listId, String newListName) {
        String sql = "UPDATE Playlist SET listName = ? WHERE listId = ?";
        return jdbcTemplate.update(sql, newListName, listId);
    }

    // 재생목록 삭제
    public int deletePlaylist(int listId) {
        String sql = "DELETE FROM Playlist WHERE listId = ?";
        return jdbcTemplate.update(sql, listId);
    }

    // Playlist 엔티티로 매핑하기 위한 RowMapper
    private static class PlaylistRowMapper implements RowMapper<Playlist> {
        @Override
        public Playlist mapRow(ResultSet rs, int rowNum) throws SQLException {
            Playlist playlist = new Playlist();
            playlist.setListId(rs.getInt("listId"));
            playlist.setUserId(rs.getInt("userId"));
            playlist.setListName(rs.getString("listName"));
            playlist.setCreationTime(rs.getTimestamp("creationTime").toLocalDateTime());
            return playlist;
        }
    }
}
