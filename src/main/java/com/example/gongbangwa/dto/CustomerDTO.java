package com.example.gongbangwa.dto;

import com.example.gongbangwa.constant.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO{

    private int cno;            //회원번호

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;       //이메일

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;//비밀번호

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;       //이름

    @NotNull(message = "전화번호는 필수 입력 값입니다.")
    private String phone;       //전화번호

    @NotBlank(message = "별명은 필수 입력 값입니다.")
    private String nickname;    //별명

    private LocalDate birth;    //생년월일

    private Role role;          //권한


    private LocalDateTime regTime;
    private LocalDateTime updateTime;
    private String createBy;


}
