package com.example.gongbangwa.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDTO {

    private int fano;    //즐겨차즌 ㄴ공방 아이디

    private int ano;

    private String atelierNm;     // 공방명

    private int atelierType;          //업종 금액

    private int atelierAdd;          //공방 주소

    private String imgUrl;      //상품 이미지 경로

}
