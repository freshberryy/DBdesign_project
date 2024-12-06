package com.example.dbdesign_project.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SongDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    // 노래 조회
    public List<Song> getSongsInPlaylist(int listId) {
        String sql = "SELECT s.songId, s.artist, s.songName, s.released_year " +
                "FROM Song s JOIN PlaylistSong ps ON s.songId = ps.songId " +
                "WHERE ps.listId = ?";
        return jdbcTemplate.query(sql, new Object[]{listId}, (rs, rowNum) ->
                new Song(
                        rs.getInt("songId"),
                        rs.getString("artist"),
                        rs.getString("songName"),
                        rs.getInt("released_year"),
                        new ArrayList<>()
                )
        );
    }

    // 노래 추가 및 songId 반환
    public int addSong(Song song) {
        String sql = "INSERT INTO Song (artist, songName, released_year) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, song.getArtist());
            ps.setString(2, song.getSongName());
            ps.setInt(3, song.getReleasedYear());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    //재생목록에 노래 추가
    public void addSongToPlaylist(int listId, int songId) {
        String sql = "INSERT INTO PlaylistSong (listId, songId) VALUES (?, ?)";
        jdbcTemplate.update(sql, listId, songId);
    }

    //재생목록에서 노래 삭제
    public void removeSongFromPlaylist(int listId, int songId) {
        String sql = "DELETE FROM PlaylistSong WHERE listId = ? AND songId = ?";
        jdbcTemplate.update(sql, listId, songId);
    }

    //재생목록에서 노래 검색
    public List<Song> searchSongsInPlaylist(int listId, String artist, String songName, Integer releasedYear) {
        StringBuilder sql = new StringBuilder(
                "SELECT s.songId, s.artist, s.songName, s.released_year " +
                        "FROM Song s JOIN PlaylistSong ps ON s.songId = ps.songId WHERE ps.listId = ?"
        );
        // 검색 조건
        if (artist != null && !artist.isEmpty()) {
            sql.append(" AND s.artist = ?");
        }
        if (songName != null && !songName.isEmpty()) {
            sql.append(" AND s.songName = ?");
        }
        if (releasedYear != null) {
            sql.append(" AND s.released_year = ?");
        }
        return jdbcTemplate.query(sql.toString(), ps -> {
                    int paramIndex = 1;
                    ps.setInt(paramIndex++, listId);
                    if (artist != null && !artist.isEmpty()) {
                        ps.setString(paramIndex++, artist);
                    }
                    if (songName != null && !songName.isEmpty()) {
                        ps.setString(paramIndex++, songName);
                    }
                    if (releasedYear != null) {
                        ps.setInt(paramIndex++, releasedYear);
                    }
                },
                (rs, rowNum) -> new Song(
                        rs.getInt("songId"),
                        rs.getString("artist"),
                        rs.getString("songName"),
                        rs.getInt("released_year"),
                        new ArrayList<>()
                )
        );
    }
    // 노래 정렬
    public List<Song> sortSongsInPlaylist(int listId, String sortBy, String order) {

        // 정렬 기준
        if (!List.of("artist", "songName", "released_year").contains(sortBy)) {
            throw new IllegalArgumentException("정렬 기준이 잘못되었습니다.");
        }
        if (!List.of("asc", "desc").contains(order)) {
            throw new IllegalArgumentException("정렬 순서가 잘못되었습니다.");
        }

        String sql = "SELECT s.songId, s.artist, s.songName, s.released_year " +
                "FROM Song s JOIN PlaylistSong ps ON s.songId = ps.songId " +
                "WHERE ps.listId = ? ORDER BY " + sortBy + " " + order;

        return jdbcTemplate.query(sql, new Object[]{listId}, (rs, rowNum) ->
                new Song(
                        rs.getInt("songId"),
                        rs.getString("artist"),
                        rs.getString("songName"),
                        rs.getInt("released_year"),
                        new ArrayList<>()
                )
        );
    }
}
