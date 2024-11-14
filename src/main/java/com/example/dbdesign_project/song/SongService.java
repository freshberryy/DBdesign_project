package com.example.dbdesign_project.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    // 노래 추가 메서드
    public String createSong(String artist, String songName, int releasedYear) {
        int result = songRepository.addSong(artist, songName, releasedYear);
        return result == 1 ? "노래가 성공적으로 추가되었습니다." : "노래 추가 실패.";
    }

    // 모든 노래 조회 메서드
    public List<Song> getAllSongs() {
        return songRepository.findAllSongs();
    }

    // 특정 ID의 노래 조회 메서드
    public Song getSongById(int songId) {
        return songRepository.findSongById(songId);
    }

    // 노래 정보 업데이트 메서드
    public String updateSong(int songId, String artist, String songName, int releasedYear) {
        int result = songRepository.updateSong(songId, artist, songName, releasedYear);
        return result == 1 ? "노래 정보가 성공적으로 업데이트되었습니다." : "노래 업데이트 실패.";
    }

    // 노래 삭제 메서드
    public String deleteSong(int songId) {
        int result = songRepository.deleteSong(songId);
        return result == 1 ? "노래가 성공적으로 삭제되었습니다." : "노래 삭제 실패.";
    }
}
