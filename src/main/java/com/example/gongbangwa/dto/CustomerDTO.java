package com.example.gongbangwa.dto;

import com.example.gongbangwa.constant.Role;
import com.example.gongbangwa.dto.base.BaseDTO;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO extends BaseDTO {

    private int cno;            //회원번호

    private String email;       //이메일
    private String password;    //비밀번호
    private String name;       //이름
    private String phone;       //전화번호
    private Role role;          //권한

    private String nickname;    //별명

    private LocalDate birth;    //생년월일
}
