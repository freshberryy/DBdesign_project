package com.example.dbdesign_project.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongTagService {

    private final SongTagRepository songTagRepository;

    @Autowired
    public SongTagService(SongTagRepository songTagRepository) {
        this.songTagRepository = songTagRepository;
    }

    // 노래에 태그 추가
    public String addTagToSong(int songId, int tagId) {
        int result = songTagRepository.addTagToSong(songId, tagId);
        return result == 1 ? "태그가 노래에 성공적으로 추가되었습니다." : "태그 추가 실패.";
    }

    // 노래에서 태그 제거
    public String removeTagFromSong(int songId, int tagId) {
        int result = songTagRepository.removeTagFromSong(songId, tagId);
        return result == 1 ? "태그가 노래에서 성공적으로 제거되었습니다." : "태그 제거 실패.";
    }

    // 특정 노래에 연결된 모든 태그 ID 조회
    public List<Integer> getTagsForSong(int songId) {
        return songTagRepository.findTagsBySongId(songId);
    }

    // 특정 태그에 연결된 모든 노래 ID 조회
    public List<Integer> getSongsForTag(int tagId) {
        return songTagRepository.findSongsByTagId(tagId);
    }
}
