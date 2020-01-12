package com.ynu.sei.auth.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Note {
    @Id
    private int id;
    private String title;
    private String content;
    private String updateTime;

    public Note() {
    }

    public Note(int id, String title, String content, String updateTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.updateTime = updateTime;
    }
}
