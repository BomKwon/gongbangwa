package com.example.gongbangwa.repository.search;

import com.example.gongbangwa.dto.search.UserSearchDTO;
import com.example.gongbangwa.entity.Memberuser;
import com.example.gongbangwa.entity.QMemberuser;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class MemberuserRepositoryCustomImpl implements MemberuserRepositoryCustom{

    private JPAQueryFactory jpaQueryFactory;

    public MemberuserRepositoryCustomImpl(EntityManager em){

        this.jpaQueryFactory = new JPAQueryFactory(em);
    }


    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        // 이메일 , 별명 , 이름   랑 검색어
        if(StringUtils.equals("email",  searchBy) ){

            return QMemberuser.memberuser.email.like("%" + searchQuery + "%");

        }else if(StringUtils.equals("nickname",  searchBy) ){

            return QMemberuser.memberuser.nickname.like("%" + searchQuery + "%");

        }else if(StringUtils.equals("name",  searchBy) ){

            return QMemberuser.memberuser.name.like("%" + searchQuery + "%");
        }

        return null;

    }

    @Override
    public Page<Memberuser> getMemberuserPage(UserSearchDTO userSearchDTO, Pageable pageable) {
        QueryResults<Memberuser> result =  jpaQueryFactory.selectFrom(QMemberuser.memberuser)
                .where( searchByLike( userSearchDTO.getSearchBy(),
                        userSearchDTO.getSearchQuery() )
                )
                .orderBy(QMemberuser.memberuser.cno.desc() )
                .offset(pageable.getOffset())           // 몇번부터 1번글부터 // 11번글 부터
                .limit(pageable.getPageSize())          //size = 10 10개씩
                .fetchResults();

        List<Memberuser> content = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
