package com.example.dbdesign_project.playlist;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlistsongs")
public class PlaylistSongController {
    private final PlaylistSongService playlistSongService;

    public PlaylistSongController(PlaylistSongService playlistSongService) {
        this.playlistSongService = playlistSongService;
    }

    // 재생목록에 노래 추가
    @PostMapping("/add")
    public ResponseEntity<String> addSongToPlaylist(@RequestParam Integer listId, @RequestParam Integer songId) {
        if (playlistSongService.addSongToPlaylist(listId, songId)) {
            return ResponseEntity.ok("노래가 재생목록에 추가되었습니다.");
        } else {
            return ResponseEntity.status(500).body("노래 추가 실패");
        }
    }

    // 재생목록에서 노래 삭제
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeSongFromPlaylist(@RequestParam Integer listId, @RequestParam Integer songId) {
        if (playlistSongService.removeSongFromPlaylist(listId, songId)) {
            return ResponseEntity.ok("노래가 재생목록에서 삭제되었습니다.");
        } else {
            return ResponseEntity.status(404).body("재생목록 또는 노래를 찾을 수 없습니다.");
        }
    }
}
