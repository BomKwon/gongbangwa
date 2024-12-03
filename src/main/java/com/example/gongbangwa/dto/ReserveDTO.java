package com.example.gongbangwa.dto;

import com.example.gongbangwa.constant.ResStatus;
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
public class ReserveDTO {

    private int rno;

    private int reservePrice;

    private LocalDateTime reserveDate;

    private ResStatus resStatus;

    private String email;

    private LocalDateTime regTime;
    private LocalDateTime updateTime;
    private String createBy;

    private List<ReserveAtelierDTO> reserveAtelierDTOS = new ArrayList<>();

    //html에서 사용
    List<ReserveDTO> reserveDTOList;


}
