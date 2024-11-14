package com.example.dbdesign_project.tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // 태그 추가
    @PostMapping("/add")
    public ResponseEntity<Tag> addTag(@RequestParam String tagName) {
        Tag tag = tagService.addTag(tagName);
        return ResponseEntity.ok(tag);
    }

    // 태그 삭제
    @DeleteMapping("/{tagId}")
    public ResponseEntity<String> deleteTag(@PathVariable Integer tagId) {
        if (tagService.deleteTag(tagId)) {
            return ResponseEntity.ok("태그 삭제 완료");
        } else {
            return ResponseEntity.status(404).body("태그를 찾을 수 없습니다.");
        }
    }
}
