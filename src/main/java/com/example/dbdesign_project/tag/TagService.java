package com.example.dbdesign_project.tag;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    // 태그 추가
    public Tag addTag(String tagName) {
        Tag tag = new Tag();
        tag.setTagName(tagName);
        tagRepository.save(tag);
        return tag;
    }

    // 태그 조회
    public Optional<Tag> findTagById(Integer tagId) {
        return tagRepository.findById(tagId);
    }

    // 태그 삭제
    public boolean deleteTag(Integer tagId) {
        return tagRepository.deleteById(tagId) > 0;
    }
}
