package com.example.dbdesign_project.song;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    // 노래 추가
    public Song addSong(String artist, String songName, Integer releasedYear) {
        Song song = new Song();
        song.setArtist(artist);
        song.setSongName(songName);
        song.setReleasedYear(releasedYear);
        songRepository.save(song);
        return song;
    }

    // 노래 조회
    public Optional<Song> findSongById(Integer songId) {
        return songRepository.findById(songId);
    }

    // 노래 삭제
    public boolean deleteSong(Integer songId) {
        return songRepository.deleteById(songId) > 0;
    }
}
