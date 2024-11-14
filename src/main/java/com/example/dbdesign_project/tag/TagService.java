package com.example.dbdesign_project.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    // 태그 추가 메서드
    public String createTag(String tagName) {
        try {
            // 태그 이름 중복 확인
            Tag existingTag = tagRepository.findTagByName(tagName);
            if (existingTag != null) {
                return "이미 존재하는 태그 이름입니다.";
            }
        } catch (Exception e) {
            // 태그가 없으면 예외 발생; 새 태그로 간주
        }

        int result = tagRepository.addTag(tagName);
        return result == 1 ? "태그가 성공적으로 추가되었습니다." : "태그 추가 실패.";
    }

    // 모든 태그 조회 메서드
    public List<Tag> getAllTags() {
        return tagRepository.findAllTags();
    }

    // 특정 ID의 태그 조회 메서드
    public Tag getTagById(int tagId) {
        return tagRepository.findTagById(tagId);
    }

    // 태그 삭제 메서드
    public String deleteTag(int tagId) {
        int result = tagRepository.deleteTag(tagId);
        return result == 1 ? "태그가 성공적으로 삭제되었습니다." : "태그 삭제 실패.";
    }
}
