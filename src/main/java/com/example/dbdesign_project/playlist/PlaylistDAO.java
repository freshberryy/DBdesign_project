package com.example.dbdesign_project.playlist;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlaylistDAO {
    private final JdbcTemplate jdbcTemplate;

    public PlaylistDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 특정 사용자에 대한 모든 재생목록 조회
    public List<Playlist> getPlaylistsByUserId(int userId) {
        String sql = "SELECT * FROM Playlist WHERE userId = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) ->
                new Playlist(
                        rs.getInt("listId"),
                        rs.getInt("userId"),
                        rs.getString("listName"),
                        rs.getTimestamp("creationTime").toLocalDateTime()
                )
        );
    }

    // 재생목록 생성
    public int createPlaylist(Playlist playlist) {
        String sql = "INSERT INTO Playlist (userId, listName) VALUES (?, ?)";
        return jdbcTemplate.update(sql, playlist.getUserId(), playlist.getListName());
    }

    // 재생목록 이름 갱신
    public int updatePlaylistName(int listId, String newName) {
        String sql = "UPDATE Playlist SET listName = ? WHERE listId = ?";
        return jdbcTemplate.update(sql, newName, listId);
    }

    // 재생목록 삭제
    public int deletePlaylist(int listId) {
        String sql = "DELETE FROM Playlist WHERE listId = ?";
        return jdbcTemplate.update(sql, listId);
    }

    // 특정 재생목록 이름 조회
    public String getPlaylistNameById(int listId) {
        String sql = "SELECT listName FROM Playlist WHERE listId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{listId}, String.class);
    }

    // 특정 재생목록 존재 여부 확인
    public boolean existsById(int playlistId) {
        String sql = "SELECT COUNT(*) FROM Playlist WHERE listId = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{playlistId}, Integer.class);
        return count != null && count > 0;
    }
}
