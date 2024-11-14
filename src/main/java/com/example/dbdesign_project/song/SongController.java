package com.example.dbdesign_project.song;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/songs")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    // 노래 추가
    @PostMapping("/add")
    public ResponseEntity<Song> addSong(
            @RequestParam String artist,
            @RequestParam String songName,
            @RequestParam Integer releasedYear) {
        Song savedSong = songService.addSong(artist, songName, releasedYear);
        return ResponseEntity.ok(savedSong);
    }

    // 노래 조회
    @GetMapping("/{songId}")
    public ResponseEntity<Song> getSongById(@PathVariable Integer songId) {
        Optional<Song> song = songService.findSongById(songId);
        return song.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    // 노래 삭제
    @DeleteMapping("/{songId}")
    public ResponseEntity<String> deleteSong(@PathVariable Integer songId) {
        if (songService.deleteSong(songId)) {
            return ResponseEntity.ok("노래 삭제 완료");
        } else {
            return ResponseEntity.status(404).body("노래를 찾을 수 없습니다.");
        }
    }
}
