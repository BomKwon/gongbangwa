package com.example.gongbangwa.dto;

import com.example.gongbangwa.dto.base.BaseDTO;
import jakarta.persistence.Column;
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

    private String nickname;    //별명

    private LocalDate birth;    //생년월일
}
