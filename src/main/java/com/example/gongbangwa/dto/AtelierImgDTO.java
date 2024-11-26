package com.example.gongbangwa.dto;

import com.example.gongbangwa.dto.base.BaseDTO;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.AtelierClassImg;
import com.example.gongbangwa.entity.AtelierImg;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtelierImgDTO extends BaseDTO {

    private int aino;

    private String imgName;     //이미지 파일명

    private String oriImgName;  //원본 이미지 파일명

    private String imgUrl;   //이미지 조회 경로

    // 메소드  EntityToDto // DtoToEntity
    private static ModelMapper modelMapper = new ModelMapper();

    public static AtelierImgDTO of(AtelierImg atelierImg){

        return modelMapper.map(atelierImg, AtelierImgDTO.class);
    }

}
