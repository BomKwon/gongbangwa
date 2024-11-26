package com.example.gongbangwa.entity;

import com.example.gongbangwa.entity.base.UserBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Table(name = "atelier")
public class Atelier extends UserBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ano;            //공방회원번호

    @Column
    private String atelierNm;    //공방명

    @Column
    private String atelierType;   //업종

    @Column
    private String atelierDetail;  //공방 설명

    @Column
    private String atelierAdd;  //공방 주소

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int atelierView;  //공방 조회수



}
