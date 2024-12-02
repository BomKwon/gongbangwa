package com.example.gongbangwa.repository.search;
import com.example.gongbangwa.dto.search.UserSearchDTO;
import com.example.gongbangwa.entity.Customer;
import com.example.gongbangwa.entity.QCustomer;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class CustomerSearchImpl extends QuerydslRepositorySupport implements CustomerSearch {


    public CustomerSearchImpl() {
        super(Customer.class);
    }


    @Override
    public Page<Customer> jpqlQuerygetCustomerPage(UserSearchDTO userSearchDTO, Pageable pageable) {
        QCustomer customer = QCustomer.customer; //q 도메인 객체 entity를 q로 바꾼것
        JPQLQuery<Customer> query = from(customer);

        BooleanBuilder booleanBuilder =new BooleanBuilder();
        LocalDateTime localDateTime = LocalDateTime.now();// 현재

        if(userSearchDTO.getRole() != null){

            booleanBuilder.and(customer.role.eq(userSearchDTO.getRole()));

        }


        if(StringUtils.equals("email", userSearchDTO.getSearchBy())){
            booleanBuilder.and(customer.email.like("%" + userSearchDTO.getSearchQuery()+ "%"));

        }else if(StringUtils.equals("nickname",  userSearchDTO.getSearchBy()) ){

            booleanBuilder.and(customer.nickname.like("%" + userSearchDTO.getSearchQuery()+ "%"));

        }else if(StringUtils.equals("name",  userSearchDTO.getSearchBy()) ){

            booleanBuilder.and(customer.name.like("%" + userSearchDTO.getSearchQuery()+ "%"));

        }

        query.where(booleanBuilder);
        System.out.println("검색조건 추가 : " + query);


        query.where(customer.cno.gt(0L));
        System.out.println("0보다 큰 조건 cno가 " + query);

        //페이징
        this.getQuerydsl().applyPagination(pageable, query);

        List<Customer> content = query.fetch(); //실행
//    customerList.forEach(customer1 -> log.info(customer1));
        long count = query.fetchCount(); //row 수
//    System.out.println(count);


        return new PageImpl<>(content, pageable, count);
    }
}
