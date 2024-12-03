package com.example.gongbangwa.dto.search;

import com.example.gongbangwa.constant.LessonStatus;
import com.example.gongbangwa.constant.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtelierSearchDTO {

    private String searchDateType;  //등록 일시
                                //상품의 등록일 기준으로
    //사장
    private Role role;

    //예약가능불가
    private LessonStatus lessonStatus;

    //지역별로
    private String area;

    //판매자로
    private String searchBy;

    //keyword
    private String searchQuery = "";


}
