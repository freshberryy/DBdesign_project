package com.example.dbdesign_project.song;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class Song {
    private Integer songId;
    private String artist;
    private String songName;
    private Integer releasedYear;
    private List<String> tags;
}
