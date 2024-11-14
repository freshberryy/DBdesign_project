package com.example.dbdesign_project.playlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    // 새로운 재생목록 추가
    public String createPlaylist(int userId, String listName) {
        int result = playlistRepository.addPlaylist(userId, listName);
        return result == 1 ? "재생목록이 성공적으로 추가되었습니다." : "재생목록 추가 실패.";
    }

    // 특정 사용자의 재생목록 조회
    public List<Playlist> getPlaylistsByUserId(int userId) {
        return playlistRepository.findPlaylistsByUserId(userId);
    }

    // 특정 재생목록 조회 (listId로)
    public Playlist getPlaylistById(int listId) {
        return playlistRepository.findPlaylistById(listId);
    }

    // 재생목록 이름 업데이트
    public String updatePlaylistName(int listId, String newListName) {
        int result = playlistRepository.updatePlaylistName(listId, newListName);
        return result == 1 ? "재생목록 이름이 성공적으로 변경되었습니다." : "재생목록 이름 변경 실패.";
    }

    // 재생목록 삭제
    public String deletePlaylist(int listId) {
        int result = playlistRepository.deletePlaylist(listId);
        return result == 1 ? "재생목록이 성공적으로 삭제되었습니다." : "재생목록 삭제 실패.";
    }
}
