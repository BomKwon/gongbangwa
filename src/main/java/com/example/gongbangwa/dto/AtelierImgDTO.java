package com.example.gongbangwa.dto;

import com.example.gongbangwa.constant.Role;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.AtelierClassImg;
import com.example.gongbangwa.entity.AtelierImg;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtelierImgDTO{

    private int aino;

    private String imgName;     //이미지 파일명

    private String oriImgName;  //원본 이미지 파일명

    private String imgUrl;   //이미지 조회 경로

    private String repimgYn;        // 대표이미지 여부

    private LocalDateTime regTime;
    private LocalDateTime updateTime;
    private String createBy;

    // 메소드  EntityToDto // DtoToEntity
    private static ModelMapper modelMapper = new ModelMapper();

    public static AtelierImgDTO of(AtelierImg atelierImg){

        return modelMapper.map(atelierImg, AtelierImgDTO.class);
    }
}
