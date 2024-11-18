package com.example.dbdesign_project.playlist;

import jakarta.servlet.http.HttpSession;
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

    @GetMapping("")
    public String playlistPage(Model model, HttpSession session) {
        // 세션에서 userId 가져오기
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login"; // 세션에 userId가 없으면 로그인 페이지로 리다이렉트
        }

        // userId에 따른 재생목록 표시
        model.addAttribute("playlists", playlistDAO.getPlaylistsByUserId(userId));
        return "playlist";
    }


    @PostMapping("/create")
    public String createPlaylist(@RequestParam int userId, @RequestParam String listName) {
        Playlist newPlaylist = new Playlist(null, userId, listName, null);
        playlistDAO.createPlaylist(newPlaylist);
        return "redirect:/playlists?userId=" + userId;
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
