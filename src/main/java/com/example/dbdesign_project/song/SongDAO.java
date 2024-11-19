package com.example.dbdesign_project.song;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SongDAO {
    private final JdbcTemplate jdbcTemplate;

    public SongDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 특정 재생목록에 포함된 모든 노래 조회
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
                        new ArrayList<>() // 기본적으로 빈 리스트 설정
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

        return keyHolder.getKey().intValue(); // 반환된 songId
    }

    // 특정 재생목록에 노래 추가
    public int addSongToPlaylist(int listId, int songId) {
        String sql = "INSERT INTO PlaylistSong (listId, songId) VALUES (?, ?)";
        return jdbcTemplate.update(sql, listId, songId);
    }

    // 특정 재생목록에서 노래 삭제
    public int removeSongFromPlaylist(int listId, int songId) {
        String sql = "DELETE FROM PlaylistSong WHERE listId = ? AND songId = ?";
        return jdbcTemplate.update(sql, listId, songId);
    }

    // 특정 재생목록에서 조건에 맞는 노래 검색
    public List<Song> searchSongsInPlaylist(int listId, String artist, String songName, Integer releasedYear) {
        StringBuilder sql = new StringBuilder(
                "SELECT s.songId, s.artist, s.songName, s.released_year " +
                        "FROM Song s JOIN PlaylistSong ps ON s.songId = ps.songId WHERE ps.listId = ?"
        );

        // 조건 추가
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


    // 특정 재생목록에서 노래 정렬
    public List<Song> sortSongsInPlaylist(int listId, String sortBy, String order) {

        // 정렬 기준 및 순서 유효성 검증
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
                        new ArrayList<>() // 기본적으로 빈 리스트 설정
                )
        );
    }
}
