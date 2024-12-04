package com.example.gongbangwa.service;
import com.example.gongbangwa.dto.LessonDTO;
import com.example.gongbangwa.entity.Lesson;
import com.example.gongbangwa.entity.LessonImg;
import com.example.gongbangwa.repository.LessonImgRepository;
import com.example.gongbangwa.repository.LessonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonImgRepository lessonImgRepository;
    private final ModelMapper modelMapper;

    private final LessonImgService lessonImgService;

    public Integer saveLesson(LessonDTO lessonDTO,
                          List<MultipartFile> multipartFiles) throws Exception{

        // modelmapper
        Lesson lesson = lessonDTO.newlesson();
//        quest.setWriter(user.getNickname());  //작성자 부분에 닉네임을 자동입력하기 위해 추가
        lessonRepository.save(lesson);  //반환이 있으니 더티


        //이미지 등록
        for (int i=0; i < multipartFiles.size(); i++){
            //입력받은 아이템이미지 숫자만큼
            //하지만 우리가 만들어놓은건 5개라 5개 들어옴
            LessonImg lessonImg = new LessonImg();
            lessonImg.setLesson(lesson);      //이 아이템은 id를 가지고 있는가 저장되는가? 더티

            if(i == 0 ){
                lessonImg.setRepimgYn("Y");   //대표이미지

            }else {
                lessonImg.setRepimgYn("N");
            }

            lessonImgService.saveLessonImg(lessonImg, multipartFiles.get(i));
        }
        return lesson.getLno();
    }


    /*수업 등록*/

    //상세정보
//    public lessonDTO lessonRead(lessonDTO lessonDTO){
//        lesson lesson = lessonRepository.findById(lessonDTO.getGno()).orElseThrow();
//        lessonDTO dto = modelMapper.map(lesson,lessonDTO.class);
//
//        List<ImgDTO> imgDTOList = lessonImgRepository.findBylesson_Gno(lessonDTO.getGno())
//                .stream().map(lessonImg ->
//                        modelMapper.map(lessonImg,ImgDTO.class))
//                .collect(Collectors.toList());
//
//        dto.setlessonImageDTOList(imgDTOList);
//
//        return dto;
//    }



}
