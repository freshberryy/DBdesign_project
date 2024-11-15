//package com.example.dbdesign_project.util;
//
//import com.example.dbdesign_project.song.Song;
//import com.example.dbdesign_project.song.SongDAO;
//import com.example.dbdesign_project.playlist.PlaylistDAO;
//import org.springframework.stereotype.Component;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class CsvToDatabaseUtil {
//    private final SongDAO songDAO;
//    private final PlaylistDAO playlistDAO;
//
//    public CsvToDatabaseUtil(SongDAO songDAO, PlaylistDAO playlistDAO) {
//        this.songDAO = songDAO;
//        this.playlistDAO = playlistDAO;
//    }
//
//    public List<String> insertSongsFromCsv(String filePath, int playlistId) {
//        List<String> errors = new ArrayList<>();
//
//        // 재생목록 검증
//        if (!playlistDAO.existsById(playlistId)) {
//            errors.add("유효하지 않은 재생목록 ID: " + playlistId);
//            return errors;
//        }
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//
//            // 파일 읽기
//            while ((line = reader.readLine()) != null) {
//                String[] data = line.split(","); // 콤마로 구분
//                if (data.length != 3) {
//                    errors.add("잘못된 데이터 형식: " + line);
//                    continue;
//                }
//
//                try {
//                    String artist = data[0].trim();
//                    String songName = data[1].trim();
//                    int releasedYear = Integer.parseInt(data[2].trim());
//
//                    // 노래를 Song 테이블에 삽입
//                    Song song = new Song(null, artist, songName, releasedYear, new ArrayList<>());
//                    int songId = songDAO.addSong(song);
//
//                    // PlaylistSong 테이블에 삽입
//                    songDAO.addSongToPlaylist(playlistId, songId);
//                } catch (Exception e) {
//                    errors.add("삽입 실패: " + line + " (" + e.getMessage() + ")");
//                }
//            }
//        } catch (Exception e) {
//            errors.add("파일 읽기 실패: " + e.getMessage());
//        }
//
//        return errors;
//    }
//}
