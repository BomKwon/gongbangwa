package com.example.gongbangwa.entity;

import com.example.gongbangwa.entity.base.Base;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
public class Review extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int arno;

    private String content;

    private String writer;

    @ManyToOne
    @JoinColumn(name = "ano")
    private Atelier atelier;

    @ManyToOne
    @JoinColumn(name = "acno")
    private Lesson lesson;

}
