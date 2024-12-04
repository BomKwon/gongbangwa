package com.example.gongbangwa.entity;

import com.example.gongbangwa.constant.Role;
import com.example.gongbangwa.dto.AtelierDTO;
import com.example.gongbangwa.entity.base.Base;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "atelier")
public class Atelier extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ano;            //공방회원번호

    @Column
    private String atelierNm;    //공방명

    @Column(unique = true)
    private String email;       //이메일

    @Column(nullable = false, length = 100)
    private String password;    //비밀번호

    @Column
    private String atelierType;   //업종

    @Column
    private String atelierDetail;  //공방 설명

    @Column
    private String atelierArea;   //지역

    @Column
    private String atelierAdd;  //공방 주소

    @Column
    private String name;       //이름

    @Column
    private String phone;       //전화번호

    @Enumerated(EnumType.STRING)
    private Role role;          //권한

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int atelierView;  //공방 조회수

    //앞 나 to 뒤 데려오는애
    @OneToMany(mappedBy = "atelier", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AtelierImg> atelierImgList = new ArrayList<>();

    //수업
    @OneToMany(mappedBy = "atelier", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Lesson> lessonList = new ArrayList<>();



    //회원가입용
    public static Atelier createAtelier(AtelierDTO atelierDTO,
                                        PasswordEncoder passwordEncoder) {
        //modelmapper
        Atelier atelier = new Atelier();
        atelier.setEmail(atelierDTO.getEmail());
        String password =  passwordEncoder.encode(atelierDTO.getPassword());
        atelier.setPassword(password);
        atelier.setName(atelierDTO.getName());
        atelier.setPhone(atelierDTO.getPhone());
        atelier.setAtelierNm(atelierDTO.getAtelierNm());
        atelier.setAtelierType(atelierDTO.getAtelierType());
        atelier.setAtelierDetail(atelierDTO.getAtelierDetail());
        atelier.setAtelierAdd(atelierDTO.getAtelierAdd());
        atelier.setRole(Role.MASTER);      // 사용자가 가입했을때

        return atelier;
    }



}
