package com.example.dbdesign_project.song;

import com.example.dbdesign_project.song.Song;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SongRepository {
    private final JdbcTemplate jdbcTemplate;

    public SongRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Song song) {
        String sql = "INSERT INTO Song (artist, songName, released_year) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, song.getArtist(), song.getSongName(), song.getReleasedYear());
    }

    public Optional<Song> findById(Integer songId) {
        String sql = "SELECT * FROM Song WHERE songId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Song.class), songId)
                .stream().findFirst();
    }

    public int deleteById(Integer songId) {
        String sql = "DELETE FROM Song WHERE songId = ?";
        return jdbcTemplate.update(sql, songId);
    }
}
