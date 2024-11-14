package com.example.dbdesign_project.playlist;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    // 재생목록 생성
    public Playlist createPlaylist(Integer userId, String listName) {
        Playlist playlist = new Playlist();
        playlist.setUserId(userId);
        playlist.setListName(listName);
        playlist.setCreationTime(LocalDateTime.now());
        playlistRepository.save(playlist);
        return playlist;
    }

    // 특정 사용자 재생목록 조회
    public List<Playlist> getPlaylistsByUser(Integer userId) {
        return playlistRepository.findByUserId(userId);
    }

    // 재생목록 삭제
    public boolean deletePlaylist(Integer listId) {
        return playlistRepository.deleteById(listId) > 0;
    }
}
