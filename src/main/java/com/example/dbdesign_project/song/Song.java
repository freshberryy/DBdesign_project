package com.example.dbdesign_project.song;

import lombok.Data;

@Data
public class Song {
    private Integer songId;
    private String artist;
    private String songName;
    private Integer releasedYear;
}
