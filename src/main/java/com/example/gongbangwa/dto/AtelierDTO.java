package com.example.gongbangwa.dto;

import com.example.gongbangwa.constant.Role;
import com.example.gongbangwa.dto.base.BaseDTO;
import com.example.gongbangwa.dto.base.UserBaseDTO;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtelierDTO extends BaseDTO {

    private int ano;            //공방회원번호

    private String atelierNm;    //공방명

    private String atelierType;   //업종

    private String atelierDetail;  //공방 설명

    private String atelierAdd;  //공방 주소

    private int atelierView;  //공방 조회수

    private String email;       //이메일
    private String password;    //비밀번호
    private String name;       //이름
    private String phone;       //전화번호
    private Role role;          //권한


}
