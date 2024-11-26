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
@Table(name = "customer")
public class Customer extends UserBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;            //회원번호

    @Column
    private String nickname;    //별명

    @Column
    private LocalDate birth;    //생년월일



}
