package com.example.gongbangwa.dto;

import com.example.gongbangwa.dto.base.BaseDTO;
import com.example.gongbangwa.entity.AtelierClassImg;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtelierClassImgDTO extends BaseDTO {

    private int id;

    private String imgName;     //이미지 파일명

    private String oriImgName;  //원본 이미지 파일명

    private String imgUrl;   //이미지 조회 경로

    private String repimgYn;        // 대표이미지 여부

    // 메소드  EntityToDto // DtoToEntity
    private static ModelMapper modelMapper = new ModelMapper();



    public static AtelierClassImgDTO of(AtelierClassImg atelierClassImg){

        return modelMapper.map(atelierClassImg, AtelierClassImgDTO.class);
    }
}
