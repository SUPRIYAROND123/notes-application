package com.disqo.notesapp.note;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="userId")
    public int userId;

    @Column(name = "title")
    public String title;

    @Column(name = "note")
    public String note;

    @Column(name = "late_update_time")
    public Date lastUpdateTime;

    @Column(name = "create_time")
    public Date createTime;

}
