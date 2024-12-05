package com.example.gongbangwa.dto;

import com.example.gongbangwa.constant.Role;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.AtelierImg;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtelierDTO{

    private Integer ano;            //공방회원번호

    @NotBlank(message = "상호명은 필수 입력 값입니다.")
    private String atelierNm;    //공방명

    @NotBlank(message = "업종은 필수 입력 값입니다.")
    private String atelierType;   //업종

    @NotBlank(message = "상세설명은 필수 입력 값입니다.")
    private String atelierDetail;  //공방 설명

    @NotBlank(message = "지역은 필수 입력 값입니다.")
    private String atelierArea;  //공방 지역

    @NotBlank(message = "사업지는 필수 입력 값입니다.")
    private String atelierAdd;  //공방 주소

    @NotBlank(message = "오픈시간은 필수 입력 값입니다.")
    private String opening;  //공방오픈시간
    @NotBlank(message = "마감시간은 필수 입력 값입니다.")
    private String closing;  //공방마감시간

    private int atelierView;  //공방 조회수

    @NotBlank(message = "사업주명은 필수 입력 값입니다.")
    private String name;       //이름 - 나중에 회원에서 자동으로 끌고오고 fk

    @NotNull(message = "전화번호는 필수 입력 값입니다.")
    private String phone;       //전화번호

    private LocalDateTime regTime;
    private LocalDateTime updateTime;
    private String createBy;

    private String imgUrl;
    private String imgName;

    private List<AtelierImgDTO> atelierImgDTOList = new ArrayList<>();
    // 여기 이미지 넣은것처럼
    // 댓글, 리뷰등을 넣을까?
    // 상세정보보는 칸에 상단을 부트스트랩에서 텝으로 여러개 띄울수있게

    //이미 저장되어서 수정할때 불러온 사진들의 아이디 삭제할 이미지들
    private List<Integer> atelierImgIds = new ArrayList<>();

    private List<LessonDTO> lessonDTOList = new ArrayList<>();
    //이미 저장되어서 수정할때 불러온 수업의 아이디 삭제할 수업
    private List<Integer> lessons = new ArrayList<>();

    private int cno;

    @QueryProjection
    public AtelierDTO(Integer ano, String atelierNm, String atelierType, String atelierDetail, String atelierArea, String atelierAdd, String opening, String closing, String phone, String imgUrl, String imgName) {
        this.ano = ano;
        this.atelierNm = atelierNm;
        this.atelierType = atelierType;
        this.atelierDetail = atelierDetail;
        this.atelierArea = atelierArea;
        this.atelierAdd = atelierAdd;
        this.opening = opening;
        this.closing = closing;
        this.phone = phone;
        this.imgUrl = imgUrl;
        this.imgName = imgName;
    }


    // 메소드  EntityToDto // DtoToEntity
    private static ModelMapper modelMapper = new ModelMapper();
    public static AtelierDTO of(Atelier atelier){
        return modelMapper.map(atelier, AtelierDTO.class);
    }


}
