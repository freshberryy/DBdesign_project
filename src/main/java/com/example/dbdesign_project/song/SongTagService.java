package com.example.dbdesign_project.song;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SongTagService {
    private final SongTagRepository songTagRepository;

    public SongTagService(SongTagRepository songTagRepository) {
        this.songTagRepository = songTagRepository;
    }

    // 노래에 태그 추가
    @Transactional
    public boolean addTagToSong(Integer songId, Integer tagId) {
        return songTagRepository.addTagToSong(songId, tagId) > 0;
    }

    // 노래에서 태그 제거
    @Transactional
    public boolean removeTagFromSong(Integer songId, Integer tagId) {
        return songTagRepository.removeTagFromSong(songId, tagId) > 0;
    }
}
