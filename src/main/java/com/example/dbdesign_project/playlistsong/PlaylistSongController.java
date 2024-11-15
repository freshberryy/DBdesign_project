package com.example.dbdesign_project.playlistsong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/playlistsong")
public class PlaylistSongController {
    private final PlaylistSongDAO playlistSongDAO;

    public PlaylistSongController(PlaylistSongDAO playlistSongDAO) {
        this.playlistSongDAO = playlistSongDAO;
    }

    // 재생목록에 노래 추가
    @PostMapping("/add")
    public String addSongToPlaylist(@RequestParam int listId, @RequestParam int songId) {
        playlistSongDAO.addSongToPlaylist(listId, songId);
        return "redirect:/songs?listId=" + listId;
    }

    // 재생목록에서 노래 삭제
    @PostMapping("/remove")
    public String removeSongFromPlaylist(@RequestParam int listId, @RequestParam int songId) {
        playlistSongDAO.removeSongFromPlaylist(listId, songId);
        return "redirect:/songs?listId=" + listId;
    }
}
