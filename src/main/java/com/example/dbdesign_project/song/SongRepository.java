package com.example.dbdesign_project.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SongRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SongRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 노래 추가 메서드
    public int addSong(String artist, String songName, int releasedYear) {
        String sql = "INSERT INTO Song (artist, songName, released_year) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, artist, songName, releasedYear);
    }

    // 모든 노래 조회 메서드
    public List<Song> findAllSongs() {
        String sql = "SELECT * FROM Song";
        return jdbcTemplate.query(sql, new SongRowMapper());
    }

    // 노래 ID로 특정 노래 조회 메서드
    public Song findSongById(int songId) {
        String sql = "SELECT * FROM Song WHERE songId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{songId}, new SongRowMapper());
    }

    // 특정 노래 정보 업데이트 메서드
    public int updateSong(int songId, String artist, String songName, int releasedYear) {
        String sql = "UPDATE Song SET artist = ?, songName = ?, released_year = ? WHERE songId = ?";
        return jdbcTemplate.update(sql, artist, songName, releasedYear, songId);
    }

    // 노래 삭제 메서드
    public int deleteSong(int songId) {
        String sql = "DELETE FROM Song WHERE songId = ?";
        return jdbcTemplate.update(sql, songId);
    }

    // Song 엔티티로 매핑하기 위한 RowMapper
    private static class SongRowMapper implements RowMapper<Song> {
        @Override
        public Song mapRow(ResultSet rs, int rowNum) throws SQLException {
            Song song = new Song();
            song.setSongId(rs.getInt("songId"));
            song.setArtist(rs.getString("artist"));
            song.setSongName(rs.getString("songName"));
            song.setReleasedYear(rs.getInt("released_year"));
            return song;
        }
    }
}
