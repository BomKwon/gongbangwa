package com.example.gongbangwa.dto;

import com.example.gongbangwa.constant.ResStatus;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.AtelierClass;
import com.example.gongbangwa.entity.Customer;
import com.example.gongbangwa.entity.base.Base;
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
public class AtelierClassDTO extends Base {

    private int acno;

    private String acNm;

    private String acDetail;

    private int acPrice;

    private int acDifficulty;

    private int acStock;

    private ResStatus resStatus; //예약 상태

    private static ModelMapper modelMapper = new ModelMapper();

    private Atelier ano;
    private Customer cno;

    private int acView;   //조회수


    public AtelierClass newAC(){

        return modelMapper.map(this, AtelierClass.class);
    }

    public static AtelierClassDTO of(AtelierClass atelierClass){

        return modelMapper.map(atelierClass, AtelierClassDTO.class);
    }


    private List<AtelierClassImgDTO> questImgDTOList = new ArrayList<>();
    // 여기 이미지 넣은것처럼
    // 댓글, 리뷰등을 넣을까?
    // 상세정보보는 칸에 상단을 부트스트랩에서 텝으로 여러개 띄울수있게

    //acImgIds
    //이미 저장되어서 수정할때 불러온 사진들의 아이디 삭제할 이미지들
    private List<Integer> acImgIds = new ArrayList<>();



}
