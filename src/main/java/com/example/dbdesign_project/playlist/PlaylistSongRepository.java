package com.example.dbdesign_project.playlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlaylistSongRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PlaylistSongRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 특정 재생목록에 노래 추가
    public int addSongToPlaylist(int listId, int songId) {
        String sql = "INSERT INTO PlaylistSong (listId, songId) VALUES (?, ?)";
        return jdbcTemplate.update(sql, listId, songId);
    }

    // 특정 재생목록에서 노래 제거
    public int removeSongFromPlaylist(int listId, int songId) {
        String sql = "DELETE FROM PlaylistSong WHERE listId = ? AND songId = ?";
        return jdbcTemplate.update(sql, listId, songId);
    }

    // 특정 재생목록의 모든 노래 조회
    public List<Integer> findSongsByPlaylistId(int listId) {
        String sql = "SELECT songId FROM PlaylistSong WHERE listId = ?";
        return jdbcTemplate.query(sql, new Object[]{listId}, (rs, rowNum) -> rs.getInt("songId"));
    }
}
