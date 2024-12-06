package com.example.dbdesign_project.song;

import com.example.dbdesign_project.playlist.PlaylistDAO;
import com.example.dbdesign_project.tag.TagDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {
    private final SongDAO songDAO;
    private final PlaylistDAO playlistDAO;
    private final TagDAO tagDAO;

    public SongController(SongDAO songDAO, PlaylistDAO playlistDAO, TagDAO tagDAO) {
        this.songDAO = songDAO;
        this.playlistDAO = playlistDAO;

        this.tagDAO = tagDAO;
    }

    // 특정 재생목록의 노래 목록 표시
    @GetMapping("")
    public String songPage(@RequestParam int listId,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           Model model) {

        int offset = (page) * size;
        List<Song> songs = songDAO.getSongs(listId, offset, size);
        int totalSongs = songDAO.countSongs(listId);
        int totalPages = (int)Math.ceil((double)totalSongs / size);

        model.addAttribute("selectedListId", listId);
        model.addAttribute("songs", songs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "song";
    }

    // 선택된 재생목록에 노래 추가
    @PostMapping("/add")
    public String addSongToPlaylist(@RequestParam int listId,
                                    @RequestParam String artist,
                                    @RequestParam String songName,
                                    @RequestParam int releasedYear) {
        songDAO.addSong(listId, artist, songName, releasedYear);
        return "redirect:/songs?listId=" + listId;
    }

    @PostMapping("/update")
    public String updateSong(@RequestParam int songId,
                             @RequestParam String artist,
                             @RequestParam String songName,
                             @RequestParam int releasedYear,
                             @RequestParam int listId) {
        songDAO.updateSong(songId, artist, songName, releasedYear);
        return "redirect:/songs?listId=" + listId;
    }


    // 선택된 재생목록에서 노래 삭제
    @PostMapping("/delete")
    public String deleteSongFromPlaylist(@RequestParam int listId, @RequestParam int songId) {
        songDAO.removeSong(listId, songId);
        return "redirect:/songs?listId=" + listId;
    }

    // 선택된 재생목록에서 노래 검색
    @GetMapping("/search")
    public String searchSongsInPlaylist(@RequestParam int listId,
                                        @RequestParam(required = false) String artist,
                                        @RequestParam(required = false) String songName,
                                        @RequestParam(required = false) Integer releasedYear,
                                        Model model) {
        List<Song> results = songDAO.searchSongs(listId, artist, songName, releasedYear);
        model.addAttribute("songs", results);
        model.addAttribute("selectedListId", listId);
        model.addAttribute("playlistName", playlistDAO.getPlaylistNameById(listId));
        return "song";
    }

    // 선택된 재생목록에서 노래 정렬
    @GetMapping("/sort")
    public String sortSongsInPlaylist(@RequestParam int listId,
                                      @RequestParam String sortBy,
                                      @RequestParam String order,
                                      Model model) {
        List<Song> sortedSongs = songDAO.sortSongs(listId, sortBy, order);
        model.addAttribute("songs", sortedSongs);
        model.addAttribute("selectedListId", listId);
        model.addAttribute("playlistName", playlistDAO.getPlaylistNameById(listId));
        return "song";
    }
}
