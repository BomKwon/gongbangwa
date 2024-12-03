package com.example.gongbangwa.dto;

import com.example.gongbangwa.constant.LessonStatus;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.Lesson;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {

    private int lno;

    private String lessonNm;

    private String lessonDetail;

    private int lessonPrice;

    private int lessonDifficulty;

    private int lessonStock;

    private LessonStatus lessonStatus; //예약 상태

    private static ModelMapper modelMapper = new ModelMapper();

    private Atelier ano;

    private int lessonView;   //조회수



    public Lesson newlesson(){

        return modelMapper.map(this, Lesson.class);
    }

    public static LessonDTO of(Lesson lesson){

        return modelMapper.map(lesson, LessonDTO.class);
    }


    private List<LessonImgDTO> lessonImgDTOList = new ArrayList<>();
    // 여기 이미지 넣은것처럼
    // 댓글, 리뷰등을 넣을까?
    // 상세정보보는 칸에 상단을 부트스트랩에서 텝으로 여러개 띄울수있게

    //lessonImgIds
    //이미 저장되어서 수정할때 불러온 사진들의 아이디 삭제할 이미지들
    private List<Integer> lessonImgIds = new ArrayList<>();



}
