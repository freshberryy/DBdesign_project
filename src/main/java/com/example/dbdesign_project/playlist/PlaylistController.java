package com.example.dbdesign_project.playlist;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/playlists")
public class PlaylistController {
    private final PlaylistDAO playlistDAO;

    public PlaylistController(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    // 재생목록 페이지로 이동 및 모든 재생목록 표시
    @GetMapping("")
    public String playlistPage(Model model, @RequestParam(required = false) Integer userId) {
        userId = (userId != null) ? userId : 1; // 기본 userId 사용 예시
        model.addAttribute("playlists", playlistDAO.getPlaylistsByUserId(userId));
        return "playlist";
    }

    // 재생목록 생성
    @PostMapping("/create")
    public String createPlaylist(@RequestParam int userId, @RequestParam String listName) {
        Playlist newPlaylist = new Playlist(null, userId, listName, null);
        playlistDAO.createPlaylist(newPlaylist);
        return "redirect:/playlists";
    }

    // 재생목록 이름 갱신
    @PostMapping("/update")
    public String updatePlaylist(@RequestParam int listId, @RequestParam String newName) {
        playlistDAO.updatePlaylistName(listId, newName);
        return "redirect:/playlists";
    }

    // 재생목록 삭제
    @PostMapping("/delete")
    public String deletePlaylist(@RequestParam int listId) {
        playlistDAO.deletePlaylist(listId);
        return "redirect:/playlists";
    }

    // 재생목록 선택 후 song 페이지로 이동
    @GetMapping("/select")
    public String selectPlaylist(@RequestParam int listId) {
        return "redirect:/songs?listId=" + listId;
    }
}
