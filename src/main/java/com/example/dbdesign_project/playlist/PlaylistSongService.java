package com.example.dbdesign_project.playlist;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaylistSongService {
    private final PlaylistSongRepository playlistSongRepository;

    public PlaylistSongService(PlaylistSongRepository playlistSongRepository) {
        this.playlistSongRepository = playlistSongRepository;
    }

    // 재생목록에 노래 추가
    @Transactional
    public boolean addSongToPlaylist(Integer listId, Integer songId) {
        // 추가 성공 시 1 이상의 값을 반환하므로 > 0으로 성공 여부 확인
        return playlistSongRepository.addSongToPlaylist(listId, songId) > 0;
    }

    // 재생목록에서 노래 제거
    @Transactional
    public boolean removeSongFromPlaylist(Integer listId, Integer songId) {
        return playlistSongRepository.removeSongFromPlaylist(listId, songId) > 0;
    }
}
