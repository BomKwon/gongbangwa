package com.example.gongbangwa.repository.search;
import com.example.gongbangwa.dto.search.UserSearchDTO;
import com.example.gongbangwa.entity.Memberuser;
import com.example.gongbangwa.entity.QMemberuser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class MemberuserSearchImpl extends QuerydslRepositorySupport implements MemberuserSearch {


    public MemberuserSearchImpl() {
        super(Memberuser.class);
    }


    @Override
    public Page<Memberuser> jpqlQuerygetMemberuserPage(UserSearchDTO userSearchDTO, Pageable pageable) {
        QMemberuser memberuser = QMemberuser.memberuser; //q 도메인 객체 entity를 q로 바꾼것
        JPQLQuery<Memberuser> query = from(memberuser);

        BooleanBuilder booleanBuilder =new BooleanBuilder();
        LocalDateTime localDateTime = LocalDateTime.now();// 현재

        if(userSearchDTO.getRole() != null){

            booleanBuilder.and(memberuser.role.eq(userSearchDTO.getRole()));

        }


        if(StringUtils.equals("email", userSearchDTO.getSearchBy())){
            booleanBuilder.and(memberuser.email.like("%" + userSearchDTO.getSearchQuery()+ "%"));

        }else if(StringUtils.equals("nickname",  userSearchDTO.getSearchBy()) ){

            booleanBuilder.and(memberuser.nickname.like("%" + userSearchDTO.getSearchQuery()+ "%"));

        }else if(StringUtils.equals("name",  userSearchDTO.getSearchBy()) ){

            booleanBuilder.and(memberuser.name.like("%" + userSearchDTO.getSearchQuery()+ "%"));

        }

        query.where(booleanBuilder);
        System.out.println("검색조건 추가 : " + query);


        query.where(memberuser.cno.gt(0L));
        System.out.println("0보다 큰 조건 cno가 " + query);

        //페이징
        this.getQuerydsl().applyPagination(pageable, query);

        List<Memberuser> content = query.fetch(); //실행
//    memberuserList.forEach(memberuser1 -> log.info(memberuser1));
        long count = query.fetchCount(); //row 수
//    System.out.println(count);


        return new PageImpl<>(content, pageable, count);
    }
}
