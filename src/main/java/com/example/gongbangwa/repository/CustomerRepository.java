package com.example.gongbangwa.repository;

import com.example.gongbangwa.entity.Customer;
import com.example.gongbangwa.repository.search.CustomerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> , CustomerRepositoryCustom {

    //이메일로 정보 찾기 //중복회원?? 혹은 로그인시?
    Customer findByEmail (String email);
    void deleteUserByEmail(String Email);
    Customer findByName(String name);
    Customer findByNameAndEmail(String name,String email);

    Customer findCustomerByEmail(String email);

}
