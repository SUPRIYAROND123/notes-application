package com.disqo.notesapp.note;

import javax.persistence.Column;
import java.util.Date;

public class NoteDto {
    private int id;
    private int userId;
    private String title;
    private String note;
    private Date lastUpdateTime;
    private Date createTime;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return this.userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public String getNote() {
        return this.note;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    public Date getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getCreateTime() {
        return this.createTime;
    }
}
