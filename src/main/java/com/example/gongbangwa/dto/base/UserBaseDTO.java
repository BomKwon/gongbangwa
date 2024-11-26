package com.example.gongbangwa.dto.base;

import com.example.gongbangwa.constant.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UserBaseDTO extends BaseDTO{
    private String email;       //이메일
    private String password;    //비밀번호
    private String name;       //이름
    private String phone;       //전화번호
    private Role role;          //권한
}
