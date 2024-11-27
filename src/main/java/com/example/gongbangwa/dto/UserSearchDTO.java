package com.example.gongbangwa.dto;

import com.example.gongbangwa.constant.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSearchDTO {

    private String searchDateType;  //가입 일시

    private String searchBy;
    //keyword
    private String searchQuery = "";

    private Role role;

}
