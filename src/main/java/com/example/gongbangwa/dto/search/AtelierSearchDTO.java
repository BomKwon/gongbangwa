package com.example.gongbangwa.dto.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtelierSearchDTO {

    private String searchDateType;  //등록 일시
                                //상품의 등록일 기준으로
    //지역별로
    private String atlierArea;

    //공방명
    private String atelierNm;

    //공방타입
    private String atelierType;

    //keyword
    private String searchQuery = "";


}
