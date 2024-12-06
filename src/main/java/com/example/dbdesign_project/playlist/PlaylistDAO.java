package com.example.dbdesign_project.playlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PlaylistDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
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

    //이름 변경
    public int updatePlaylistName(int listId, String newName) {
        String sql = "UPDATE Playlist SET listName = ? WHERE listId = ?";
        return jdbcTemplate.update(sql, newName, listId);
    }

    // 삭제
    public int deletePlaylist(int listId) {
        String sql = "DELETE FROM Playlist WHERE listId = ?";
        return jdbcTemplate.update(sql, listId);
    }

    //조회
    public String getPlaylistNameById(int listId) {
        String sql = "SELECT listName FROM Playlist WHERE listId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{listId}, String.class);
    }

}
