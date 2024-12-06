package com.example.dbdesign_project.tag;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/songtag")
public class TagController {
    @Autowired
    private TagDAO tagDAO;

    // 노래에 태그 추가
    @PostMapping("/add")
    public String addTagToSong(@RequestParam int songId,
                               @RequestParam String tagName,
                               @RequestParam int listId) {
        tagDAO.addTagToSong(songId, tagName.trim());
        return "redirect:/songs?listId=" + listId;
    }

    // 노래에서 태그 삭제
    @PostMapping("/remove")
    public String removeTagFromSong(@RequestParam int songId,
                                    @RequestParam String tagName,
                                    @RequestParam int listId) {
        tagDAO.removeTagFromSong(songId, tagName.trim());
        return "redirect:/songs?listId=" + listId;
    }
}
