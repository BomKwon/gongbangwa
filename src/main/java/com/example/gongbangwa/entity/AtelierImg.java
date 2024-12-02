package com.example.gongbangwa.entity;


import com.example.gongbangwa.entity.base.Base;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = "atelier")
@Table(name = "atelier_img")
public class AtelierImg extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aino;

    @Column
    private String imgName;     //이미지 파일명

    @Column
    private String oriImgName;  //원본 이미지 파일명

    @Column
    private String imgUrl;   //이미지 조회 경로

    @Column
    private String repimgYn;        // 대표이미지 여부

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ano")
    private Atelier atelier;


    public void updateAtelierImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

}
