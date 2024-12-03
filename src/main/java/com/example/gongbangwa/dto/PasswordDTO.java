package com.example.gongbangwa.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDTO {


    @Length(min = 8 , max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;

    @Length(min = 8 , max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password2;
    // 새로 받을 비번

    private String conpassword;
    //입력값 일치 확인용

    private String email;

    private String name;

}
