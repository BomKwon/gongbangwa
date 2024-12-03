package com.example.gongbangwa.repository;

import com.example.gongbangwa.entity.AtelierImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtelierImgRepository extends JpaRepository<AtelierImg, Integer> {

    List<AtelierImg> findByAinoOrderByAinoAsc(Long aino);

    //상품의 대표이미지를 찾는 쿼리 메소드
    AtelierImg findByAinoAndRepimgYn (Long aino, String repimgYn);

    List<AtelierImg> findByAtelier_Ano(int ano);

    Integer deleteByAtelier_Ano(int ano);

}
