package com.example.gongbangwa.service;
import com.example.gongbangwa.dto.AtelierDTO;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.AtelierImg;
import com.example.gongbangwa.entity.Customer;
import com.example.gongbangwa.repository.AtelierImgRepository;
import com.example.gongbangwa.repository.AtelierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AtelierService {


    private final AtelierRepository atelierRepository;
    // itemImg 정보저장
    private final AtelierImgService atelierImgService;

    private void validateDuplicate(Atelier atelier) {
        log.info("이미 가입된 회원인지 확인");
        // 데이터베이스에 저장된 회원가입이 되어있는지 찾아본다.
        Atelier findMemberE = atelierRepository.findByEmail(atelier.getEmail());

        //이미 가입된 email이라면
        if(findMemberE != null) {
            log.info("이미 가입된 회원");
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

    }


    public Integer saveAterlier(Atelier atelier,
                                List<MultipartFile> multipartFiles) throws Exception{

        log.info("사용자가 있는지 확인하기 전");

        // 사용자가 이미 있는지 찾기
        validateDuplicate(atelier);

        log.info("가입된 사용자가 없어서 저장하기 전");


        //이미지 등록
        for (int i=0; i < multipartFiles.size(); i++){
            //입력받은 아이템이미지 숫자만큼
            //하지만 우리가 만들어놓은건 5개라 5개 들어옴
            AtelierImg atelierImg = new AtelierImg();
            atelierImg.setAtelier(atelier);      //이 아이템은 id를 가지고 있는가 저장되는가? 더티

            if(i == 0 ){
                atelierImg.setRepimgYn("Y");   //대표이미지

            }else {
                atelierImg.setRepimgYn("N");
            }

            atelierImgService.saveAtelierImg(atelierImg, multipartFiles.get(i));

        }
        return atelier.getAno();
    }





}
