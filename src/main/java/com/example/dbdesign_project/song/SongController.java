package com.example.dbdesign_project.song;

import com.example.dbdesign_project.playlist.PlaylistDAO;
import com.example.dbdesign_project.songtag.SongTagDAO;
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
    private final SongTagDAO songTagDAO;

    public SongController(SongDAO songDAO, PlaylistDAO playlistDAO, SongTagDAO songTagDAO) {
        this.songDAO = songDAO;
        this.playlistDAO = playlistDAO;

        this.songTagDAO = songTagDAO;
    }

    // 특정 재생목록의 노래 목록 표시
    @GetMapping("")
    public String songPage(@RequestParam int listId, Model model) {
        List<Song> songs = songDAO.getSongsInPlaylist(listId);

        // 각 노래에 태그 목록을 설정
        for (Song song : songs) {
            List<String> tags = songTagDAO.getTagsForSong(song.getSongId());
            song.setTags(tags != null ? tags : new ArrayList<>()); // null이면 빈 리스트 설정
        }

        model.addAttribute("selectedListId", listId);
        model.addAttribute("songs", songs);
        return "song";
    }

    // 선택된 재생목록에 노래 추가
    @PostMapping("/add")
    public String addSongToPlaylist(@RequestParam int listId,
                                    @RequestParam String artist,
                                    @RequestParam String songName,
                                    @RequestParam int releasedYear) {
        // 새 노래 추가 및 songId 가져오기
        Song newSong = new Song(null, artist, songName, releasedYear, null);
        int songId = songDAO.addSong(newSong); // songId 반환
        newSong.setSongId(songId); // Song 객체에 songId 설정

        // 재생목록에 추가
        songDAO.addSongToPlaylist(listId, newSong.getSongId());

        return "redirect:/songs?listId=" + listId;
    }


    // 선택된 재생목록에서 노래 삭제
    @PostMapping("/delete")
    public String deleteSongFromPlaylist(@RequestParam int listId, @RequestParam int songId) {
        songDAO.removeSongFromPlaylist(listId, songId);
        return "redirect:/songs?listId=" + listId;
    }

    // 선택된 재생목록에서 노래 검색
    @GetMapping("/search")
    public String searchSongsInPlaylist(@RequestParam int listId,
                                        @RequestParam(required = false) String artist,
                                        @RequestParam(required = false) String songName,
                                        @RequestParam(required = false) Integer releasedYear,
                                        Model model) {
        List<Song> results = songDAO.searchSongsInPlaylist(listId, artist, songName, releasedYear);
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
        List<Song> sortedSongs = songDAO.sortSongsInPlaylist(listId, sortBy, order);
        model.addAttribute("songs", sortedSongs);
        model.addAttribute("selectedListId", listId);
        model.addAttribute("playlistName", playlistDAO.getPlaylistNameById(listId));
        return "song";
    }
}
