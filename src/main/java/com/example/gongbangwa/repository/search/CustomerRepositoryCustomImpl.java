package com.example.gongbangwa.repository.search;

import com.example.gongbangwa.dto.UserSearchDTO;
import com.example.gongbangwa.entity.Customer;
import com.example.gongbangwa.entity.QCustomer;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class CustomerRepositoryCustomImpl implements CustomerRepositoryCustom {

    private JPAQueryFactory jpaQueryFactory;

    public CustomerRepositoryCustomImpl(EntityManager em){

        this.jpaQueryFactory = new JPAQueryFactory(em);
    }


    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        // 이메일 , 별명 , 이름   랑 검색어
        if(StringUtils.equals("email",  searchBy) ){

            return QCustomer.customer.email.like("%" + searchQuery + "%");

        }else if(StringUtils.equals("nickname",  searchBy) ){

            return QCustomer.customer.nickname.like("%" + searchQuery + "%");

        }else if(StringUtils.equals("name",  searchBy) ){

            return QCustomer.customer.name.like("%" + searchQuery + "%");
        }

        return null;

    }

    @Override
    public Page<Customer> getCustomerPage(UserSearchDTO userSearchDTO, Pageable pageable) {
        QueryResults<Customer> result =  jpaQueryFactory.selectFrom(QCustomer.customer)
                .where( searchByLike( userSearchDTO.getSearchBy(),
                                userSearchDTO.getSearchQuery() )
                )
                .orderBy(QCustomer.customer.cno.desc() )
                .offset(pageable.getOffset())           // 몇번부터 1번글부터 // 11번글 부터
                .limit(pageable.getPageSize())          //size = 10 10개씩
                .fetchResults();

        List<Customer> content = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

}
