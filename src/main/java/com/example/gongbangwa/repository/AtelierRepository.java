package com.example.gongbangwa.repository;

import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.Customer;
import com.example.gongbangwa.repository.search.AtelierRepositoryCustorm;
import com.example.gongbangwa.repository.search.AtelierSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AtelierRepository extends JpaRepository<Atelier, Integer>, AtelierRepositoryCustorm, AtelierSearch {

    //이메일로 정보 찾기 //중복회원?? 혹은 로그인시?
    Atelier findByEmail (String email);

    /*조회수 업뎃*/
    @Modifying
    @Query("update Atelier a set a.atelierView = a.atelierView + 1 where a.ano = :ano")
    int updateAtelierViews(@Param("ano") int ano);

    /*공방명, 카테고리 검색*/
    @Query("select a from Atelier a where a.atelierNm = :atelierNm or a.atelierDetail = :atelierDetail")
    List<Atelier> findByAtelierNmOrAtelierDetail (@Param("atelierNm") String atelierNm,@Param("atelierDetail") String atelierDetail);

//    /*조회수 순*/
//    List<Atelier> findByAtelierNmOrderByAtelierViewDesc (Integer atelierView); //내림차(높은)순




}
