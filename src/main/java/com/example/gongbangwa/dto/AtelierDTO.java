package com.example.gongbangwa.dto;

import com.example.gongbangwa.dto.base.UserBaseDTO;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtelierDTO extends UserBaseDTO {

    private int ano;            //공방회원번호

    private String atelierNm;    //공방명

    private String atelierType;   //업종

    private String atelierDetail;  //공방 설명

    private String atelierAdd;  //공방 주소

    private int atelierView;  //공방 조회수

}
