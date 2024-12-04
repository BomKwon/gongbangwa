package com.example.gongbangwa.dto;

import com.example.gongbangwa.constant.Role;
import jakarta.validation.constraints.NotBlank;

public class ReadNameDTO {

    private String nickname;        //별명
    private String atelierNm;       //공방명
    private Role role;              //권한

}
