package com.example.gongbangwa.repository.search;

import com.example.gongbangwa.dto.search.UserSearchDTO;
import com.example.gongbangwa.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerSearch {

    Page<Customer> jpqlQuerygetCustomerPage(UserSearchDTO userSearchDTO, Pageable pageable);
}
