package com.example.gongbangwa.repository.search;

import com.example.gongbangwa.dto.search.AtelierSearchDTO;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.QAtelier;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class AtelierSearchImpl extends QuerydslRepositorySupport implements AtelierSearch {


  public AtelierSearchImpl() {
    super(Atelier.class);
  }




  @Override
  public Page<Atelier> jpqlQuerygetAtelierPage(AtelierSearchDTO atelierSearchDTO, Pageable pageable) {

    QAtelier atelier = QAtelier.atelier; //q 도메인 객체 entity를 q로 바꾼것
    JPQLQuery<Atelier> query = from(atelier);

    BooleanBuilder booleanBuilder =new BooleanBuilder();
    LocalDateTime localDateTime = LocalDateTime.now();// 현재

    if(atelierSearchDTO.getRole() != null){

      booleanBuilder.and(atelier.role.eq(atelierSearchDTO.getRole()));

    }

    if(StringUtils.equals("all", atelierSearchDTO.getSearchDateType())  || atelierSearchDTO.getSearchDateType() == null){

    }else {
      if(StringUtils.equals("1d", atelierSearchDTO.getSearchDateType())){
        localDateTime = localDateTime.minusDays(1);
      }else if(StringUtils.equals("1w", atelierSearchDTO.getSearchDateType())){
        localDateTime = localDateTime.minusWeeks(1);
      }else if(StringUtils.equals("1m", atelierSearchDTO.getSearchDateType())){
        localDateTime = localDateTime.minusMonths(1);
      }else if(StringUtils.equals("6m", atelierSearchDTO.getSearchDateType())){
        localDateTime = localDateTime.minusMonths(6);
      }

      booleanBuilder.and(atelier.regTime.after(localDateTime));
    }

    if(StringUtils.equals("title", atelierSearchDTO.getSearchBy())){
      booleanBuilder.and(atelier.atelierNm.like("%" + atelierSearchDTO.getSearchQuery()+ "%"));

    }else if(StringUtils.equals("atelierType",  atelierSearchDTO.getSearchBy()) ){

      booleanBuilder.and(atelier.atelierType.like("%" + atelierSearchDTO.getSearchQuery()+ "%"));

    }else if(StringUtils.equals("atelierAdd",  atelierSearchDTO.getSearchBy()) ){

      booleanBuilder.and(atelier.atelierAdd.like("%" + atelierSearchDTO.getSearchQuery()+ "%"));

    }

    query.where(booleanBuilder);
    System.out.println("검색조건 추가 : " + query);


    query.where(atelier.ano.gt(0L));
    System.out.println("0보다 큰 조건 id가 " + query);

    //페이징
    this.getQuerydsl().applyPagination(pageable, query);

    List<Atelier> content = query.fetch(); //실행
//    boardList.forEach(board1 -> log.info(board1));
    long count = query.fetchCount(); //row 수
//    System.out.println(count);


    return new PageImpl<>(content, pageable, count);
  }

//  @Override
//  public Page<MainQuestDTO> searchQuest(String[] types, String keyword, Pageable pageable) {
//    QAtelier quest = QAtelier.quest; //q 도메인 객체 entity를 q로 바꾼것
//
//    JPQLQuery<Quest> query = from(quest);
//    //select board from board
//    query.groupBy(quest);
//
//
//
//    if ( (types != null && types.length > 0 && keyword != null) ){
//      BooleanBuilder booleanBuilder =new BooleanBuilder();
//
//      for (String string : types){
//
//        switch (string){
//          case "t":
//            booleanBuilder.or(quest.title.contains(keyword));
//            break;
//          case "a":
//            booleanBuilder.or(quest.area.contains(keyword));
//            break;
//
//        } //swich
//
//      }//for
//      query.where(booleanBuilder);
//      System.out.println("검색조건 추가 : " + query);
//    }//if
//
//    query.where(quest.id.gt(0L));
//    System.out.println("0보다 큰 조건 id가" + query);
//
//    JPQLQuery<MainQuestDTO> dtoQuery = query.select(Projections.bean(MainQuestDTO.class,
//            quest.id,
//            quest.title,
//            quest.writer,
//            quest.questStatus,
//            quest.view,
//            quest.questImgList,
//            quest.regTime
//    ));
//
//
//
//    //페이징
//    this.getQuerydsl().applyPagination(pageable, dtoQuery);
//    System.out.println("페이지어블" + pageable);
//
//    List<MainQuestDTO> dtoList = dtoQuery.fetch(); //실행
//    dtoList.forEach(mainQuestDTO -> System.out.println(mainQuestDTO));
////    boardList.forEach(board1 -> log.info(board1));
//    long count = dtoQuery.fetchCount(); //row 수
//
//
//    System.out.println(count);
//
//
//    return new PageImpl<>(dtoList, pageable, count);
//
//  }


}