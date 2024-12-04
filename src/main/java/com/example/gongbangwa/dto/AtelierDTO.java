package com.example.gongbangwa.dto;

import com.example.gongbangwa.constant.Role;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    private int atelierView;  //공방 조회수

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;       //이메일

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;//비밀번호

    @NotBlank(message = "사업주명은 필수 입력 값입니다.")
    private String name;       //이름

    @NotNull(message = "전화번호는 필수 입력 값입니다.")
    private String phone;       //전화번호

    private Role role;          //권한


    private LocalDateTime regTime;
    private LocalDateTime updateTime;
    private String createBy;


    private List<AtelierImgDTO> atelierImgDTOList = new ArrayList<>();
    // 여기 이미지 넣은것처럼
    // 댓글, 리뷰등을 넣을까?
    // 상세정보보는 칸에 상단을 부트스트랩에서 텝으로 여러개 띄울수있게

    //이미 저장되어서 수정할때 불러온 사진들의 아이디 삭제할 이미지들
    private List<Integer> atelierImgIds = new ArrayList<>();

    private List<LessonDTO> lessonDTOList = new ArrayList<>();
    //이미 저장되어서 수정할때 불러온 수업의 아이디 삭제할 수업
    private List<Integer> lessons = new ArrayList<>();


    @QueryProjection
    public AtelierDTO(Integer ano, String atelierNm, String atelierType, String atelierArea, String atelierAdd) {
        this.ano = ano;
        this.atelierNm = atelierNm;
        this.atelierType = atelierType;
        this.atelierArea = atelierArea;
        this.atelierAdd = atelierAdd;
    }


}
