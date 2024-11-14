package com.example.dbdesign_project.playlist;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Playlist {
    private Integer listId;
    private Integer userId;
    private String listName;
    private LocalDateTime creationTime;
}
