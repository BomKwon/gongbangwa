package com.example.gongbangwa.repository.search;

import com.example.gongbangwa.dto.AtelierDTO;
import com.example.gongbangwa.dto.QAtelierDTO;
import com.example.gongbangwa.dto.search.AtelierSearchDTO;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.QAtelier;
import com.example.gongbangwa.entity.QAtelierImg;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class AtelierRepositoryCustormImpl implements AtelierRepositoryCustorm {

    private JPAQueryFactory jpaQueryFactory;

    public AtelierRepositoryCustormImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    //상호명검색 like
    private BooleanExpression titleLike(String searchQuery){
        return StringUtils
                .isEmpty(searchQuery) ? null : QAtelier.atelier.atelierNm.like("%" + searchQuery + "%");
    }


    private BooleanExpression regDtsAfter(String searchDateType){
        // 6개월전, 1년전 1달전 1주일전

        LocalDateTime localDateTime = LocalDateTime.now();// 현재

        if(StringUtils.equals("all", searchDateType) || searchDateType == null ){
            return null;        /////////////////////////// 여기다 지워야함
        }else if(StringUtils.equals("1d", searchDateType)  ){
            localDateTime = localDateTime.minusDays(1);
        }else if(StringUtils.equals("1w", searchDateType)  ){
            localDateTime = localDateTime.minusWeeks(1);
        }else if(StringUtils.equals("1m", searchDateType)  ){
            localDateTime = localDateTime.minusMonths(1);
        }else if(StringUtils.equals("6m", searchDateType)  ){
            localDateTime = localDateTime.minusMonths(6);
        }

        return QAtelier.atelier.regTime.after(localDateTime);

    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        // 제목 , 제목+내용 , 제목 + 내용 + 작성자   랑 검색어
        if(StringUtils.equals("atelierNm",  searchBy) ){

            return QAtelier.atelier.atelierNm.like("%" + searchQuery + "%");

        }else if(StringUtils.equals("atelierType",  searchBy) ){

            return QAtelier.atelier.atelierType.like("%" + searchQuery + "%");

        }else if(StringUtils.equals("atelierAdd",  searchBy) ){

            return QAtelier.atelier.atelierAdd.like("%" + searchQuery + "%");
        }

        return null;

    }


    @Override
    public Page<Atelier> getAtelierPage(AtelierSearchDTO atelierSearchDTO, Pageable pageable) {
        //판매중/미판매중 , 날짜 몇달전꺼? , 검색어로 검색

             QueryResults<Atelier> result =  jpaQueryFactory.selectFrom(QAtelier.atelier)
                .where( regDtsAfter(atelierSearchDTO.getSearchDateType()),
                        searchByLike( atelierSearchDTO.getSearchQuery(), atelierSearchDTO.getSearchQuery() )
                        )
                .orderBy(QAtelier.atelier.ano.desc() )
                .offset(pageable.getOffset())           // 몇번부터 1번글부터 // 11번글 부터
                .limit(pageable.getPageSize())          //size = 10 10개씩
                .fetchResults();

             List<Atelier> content = result.getResults();
             long total = result.getTotal();

             /////////////////////////////////////////////////////////////////////////



             return new PageImpl<>(content, pageable, total);


    }


    @Override
    public Page<AtelierDTO> getMainAtelierPage(AtelierSearchDTO atelierSearchDTO, Pageable pageable) {
        QAtelier atelier = QAtelier.atelier;
        QAtelierImg atelierImg = QAtelierImg.atelierImg;

        QueryResults<AtelierDTO> result =  jpaQueryFactory.select(
                    new QAtelierDTO(
                            atelier.ano,
                            atelier.atelierNm,
                            atelier.atelierType,
                            atelier.atelierArea,
                            atelier.atelierAdd
                    )
                )
                .from(atelierImg)
                .join(atelierImg.atelier, atelier)
                .where(atelierImg.repimgYn.eq("Y"))    //대표이미지
                .where(titleLike(atelierSearchDTO.getSearchQuery()))  //공방이름검색 입력받은것과 같은거
                .orderBy(atelierImg.aino.desc() )
                .offset(pageable.getOffset())           // 몇번부터 1번글부터 // 11번글 부터
                .limit(pageable.getPageSize())          //size = 10 10개씩
                .fetchResults();

        List<AtelierDTO> content = result.getResults();
        long total = result.getTotal();


        return new PageImpl<>(content, pageable, total);


    }

//    @Override
//    public Page<MainQuestDTO> getUserQuestPage(AtelierSearchDTO atelierSearchDTO, Pageable pageable) {
//
//        QAtelier quest = QAtelier.quest;
//        QAtelierImg questImg = QAtelierImg.questImg;
//
//        QueryResults<MainQuestDTO> result =  jpaQueryFactory.select(
//                        new QMainQuestDTO(
//                                quest.id,
//                                quest.writer,
//                                quest.title,
//                                quest.salaryOption,
//                                quest.salary,
//                                questImg.imgUrl,
//                                quest.area,
//                                quest.questDetail,
//                                quest.questStatus
//                        )
//                )
//                .from(questImg)
//                .join(questImg.quest, quest)
//                .where(questImg.repimgYn.eq("Y"))    //대표이미지
//                .where(titleLike(atelierSearchDTO.getSearchQuery()))  //상품명검색 입력받은것과 같은거
//                .orderBy(questImg.id.desc() )
//                .offset(pageable.getOffset())           // 몇번부터 1번글부터 // 11번글 부터
//                .limit(pageable.getPageSize())          //size = 10 10개씩
//                .fetchResults();
//
//        List<MainQuestDTO> content = result.getResults();
//        long total = result.getTotal();
//
//
//        return new PageImpl<>(content, pageable, total);
//
//    }


}
