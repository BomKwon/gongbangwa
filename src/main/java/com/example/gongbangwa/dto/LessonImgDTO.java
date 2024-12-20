package com.example.gongbangwa.dto;

import com.example.gongbangwa.entity.LessonImg;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonImgDTO {

    private int lino;

    private String imgName;     //이미지 파일명

    private String oriImgName;  //원본 이미지 파일명

    private String imgUrl;   //이미지 조회 경로

    private String repimgYn;        // 대표이미지 여부


    private LocalDateTime regTime;
    private LocalDateTime updateTime;
    private String createBy;



    // 메소드  EntityToDto // DtoToEntity
    private static ModelMapper modelMapper = new ModelMapper();



    public static LessonImgDTO of(LessonImg lessonImg){

        return modelMapper.map(lessonImg, LessonImgDTO.class);
    }
}
