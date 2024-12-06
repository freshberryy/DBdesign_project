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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Song> getSongs(int listId, int offset, int limit) {
        String sql = "select s.songId, s.artist, s.songName, s.released_year " +
                "from Song s join PlaylistSong ps on s.songId = ps.songId " +
                "where ps.listId = ? limit ? offset ?";
        return jdbcTemplate.query(sql, new Object[]{listId, limit, offset}, (rs, rowNum) ->
                new Song(
                        rs.getInt("songId"),
                        rs.getString("artist"),
                        rs.getString("songName"),
                        rs.getInt("released_year"),
                        new ArrayList<>()
                )
        );
    }

    public int countSongs(int listId) {
        String sql = "select count(*) from PlaylistSong where listId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{listId}, Integer.class);
    }

    // 노래 추가
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addSong(int listId, String artist, String songName, int releasedYear) {
        String sql = "insert into Song(artist, songName, released_year) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, artist);
            ps.setString(2, songName);
            ps.setInt(3, releasedYear);
            return ps;
        }, keyHolder);

        int songId = keyHolder.getKey().intValue();

        String sql2 = "insert into PlaylistSong(listId, songId) VALUES (?, ?)";
        jdbcTemplate.update(sql2, listId, songId);
    }

    //노래 삭제
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void removeSong(int listId, int songId) {
        String sql = "delete from PlaylistSong where listId = ? AND songId = ?";
        jdbcTemplate.update(sql, listId, songId);
    }

    //노래 갱신
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateSong(int songId, String artist, String songName, int releasedYear) {
        String sql = "update Song set artist = ?, songName = ?, released_year = ? where songId = ?";
        jdbcTemplate.update(sql, artist, songName, releasedYear, songId);
    }

    //노래 검색
    public List<Song> searchSongs(int listId, String artist, String songName, Integer releasedYear) {
        StringBuilder sql = new StringBuilder(
                "select s.songId, s.artist, s.songName, s.released_year " +
                        "from Song s join PlaylistSong ps on s.songId = ps.songId where ps.listId = ?"
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
    public List<Song> sortSongs(int listId, String sortBy, String order) {

        // 정렬 기준
        if (!List.of("artist", "songName", "released_year").contains(sortBy)) {
            throw new IllegalArgumentException("정렬 기준이 잘못되었습니다.");
        }
        if (!List.of("asc", "desc").contains(order)) {
            throw new IllegalArgumentException("정렬 순서가 잘못되었습니다.");
        }

        String sql = "select s.songId, s.artist, s.songName, s.released_year " +
                "from Song s join PlaylistSong ps on s.songId = ps.songId " +
                "where ps.listId = ? order by " + sortBy + " " + order;

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
