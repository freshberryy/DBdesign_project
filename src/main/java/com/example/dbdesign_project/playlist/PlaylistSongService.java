package com.example.dbdesign_project.playlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistSongService {

    private final PlaylistSongRepository playlistSongRepository;

    @Autowired
    public PlaylistSongService(PlaylistSongRepository playlistSongRepository) {
        this.playlistSongRepository = playlistSongRepository;
    }

    // 재생목록에 노래 추가
    public String addSongToPlaylist(int listId, int songId) {
        int result = playlistSongRepository.addSongToPlaylist(listId, songId);
        return result == 1 ? "노래가 재생목록에 추가되었습니다." : "노래 추가 실패.";
    }

    // 재생목록에서 노래 제거
    public String removeSongFromPlaylist(int listId, int songId) {
        int result = playlistSongRepository.removeSongFromPlaylist(listId, songId);
        return result == 1 ? "노래가 재생목록에서 삭제되었습니다." : "노래 삭제 실패.";
    }

    // 재생목록에 포함된 모든 노래 ID 조회
    public List<Integer> getSongsInPlaylist(int listId) {
        return playlistSongRepository.findSongsByPlaylistId(listId);
    }
}
