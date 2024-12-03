package com.example.gongbangwa.entity;

import com.example.gongbangwa.entity.base.Base;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Lesson_img")
public class LessonImg extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lino;

    @Column
    private String imgName;     //이미지 파일명

    @Column
    private String oriImgName;  //원본 이미지 파일명

    @Column
    private String imgUrl;   //이미지 조회 경로

    @Column
    private String repimgYn;        // 대표이미지 여부

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "acno")
    private Lesson lesson;


    public void updateLessonImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
