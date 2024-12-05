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

    @Column
    private String atelierType;   //업종

    @Column
    private String atelierDetail;  //공방 설명

    @Column
    private String atelierArea;   //지역

    @Column
    private String atelierAdd;  //공방 주소

    @Column
    private String opening;  //공방오픈시간
    @Column
    private String closing;  //공방마감시간

    @Column
    private String name;       //이름

    @Column
    private String phone;       //전화번호


    @Column(columnDefinition = "integer default 0", nullable = false)
    private int atelierView;  //공방 조회수

    //앞 나 to 뒤 데려오는애
    @OneToMany(mappedBy = "atelier", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AtelierImg> atelierImgList = new ArrayList<>();

    //등록
    public static Atelier createAtelier(AtelierDTO atelierDTO) {
        //modelmapper
        Atelier atelier = new Atelier();
        atelier.setName(atelierDTO.getName());
        atelier.setPhone(atelierDTO.getPhone());
        atelier.setAtelierNm(atelierDTO.getAtelierNm());
        atelier.setAtelierType(atelierDTO.getAtelierType());
        atelier.setAtelierDetail(atelierDTO.getAtelierDetail());
        atelier.setAtelierAdd(atelierDTO.getAtelierAdd());
        atelier.setAtelierArea(atelierDTO.getAtelierArea());
        atelier.setOpening(atelierDTO.getOpening());
        atelier.setClosing(atelierDTO.getClosing());

        return atelier;
    }

    //수정
    public void updateAtelier (AtelierDTO atelierDTO) {
        this.atelierNm = atelierDTO.getAtelierNm();
        this.atelierArea = atelierDTO.getAtelierArea();
        this.atelierAdd = atelierDTO.getAtelierAdd();
        this.atelierDetail = atelierDTO.getAtelierDetail();
        this.opening = atelierDTO.getOpening();
        this.closing = atelierDTO.getClosing();
        this.phone = atelierDTO.getPhone();
    }



}
