package com.example.dbdesign_project.playlist;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {
    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    // 재생목록 생성
    @PostMapping("/create")
    public ResponseEntity<Playlist> createPlaylist(@RequestParam Integer userId, @RequestParam String listName) {
        Playlist playlist = playlistService.createPlaylist(userId, listName);
        return ResponseEntity.ok(playlist);
    }

    // 사용자별 재생목록 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Playlist>> getUserPlaylists(@PathVariable Integer userId) {
        List<Playlist> playlists = playlistService.getPlaylistsByUser(userId);
        return ResponseEntity.ok(playlists);
    }

    // 재생목록 삭제
    @DeleteMapping("/{listId}")
    public ResponseEntity<String> deletePlaylist(@PathVariable Integer listId) {
        if (playlistService.deletePlaylist(listId)) {
            return ResponseEntity.ok("재생목록 삭제 완료");
        } else {
            return ResponseEntity.status(404).body("재생목록을 찾을 수 없습니다.");
        }
    }
}
