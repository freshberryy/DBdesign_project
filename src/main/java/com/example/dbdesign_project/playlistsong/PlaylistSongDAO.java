package com.example.dbdesign_project.playlistsong;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlaylistSongDAO {
    private final JdbcTemplate jdbcTemplate;

    public PlaylistSongDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 재생목록에 노래 추가
    public int addSongToPlaylist(int listId, int songId) {
        String sql = "INSERT INTO PlaylistSong (listId, songId) VALUES (?, ?)";
        return jdbcTemplate.update(sql, listId, songId);
    }

    // 재생목록에서 노래 삭제
    public int removeSongFromPlaylist(int listId, int songId) {
        String sql = "DELETE FROM PlaylistSong WHERE listId = ? AND songId = ?";
        return jdbcTemplate.update(sql, listId, songId);
    }

    // 특정 재생목록의 노래 개수 확인
    public int countSongsInPlaylist(int listId) {
        String sql = "SELECT COUNT(*) FROM PlaylistSong WHERE listId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{listId}, Integer.class);
    }
}
