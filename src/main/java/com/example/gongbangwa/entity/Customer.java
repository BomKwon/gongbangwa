package com.example.gongbangwa.entity;

import com.example.gongbangwa.constant.Role;
import com.example.gongbangwa.dto.CustomerDTO;
import com.example.gongbangwa.entity.base.UserBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Table(name = "customer")
public class Customer extends UserBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;            //회원번호

    @Column
    private String nickname;    //별명

    @Column
    private LocalDate birth;    //생년월일


    //회원가입용
    public static Customer createCustomer(CustomerDTO customerDTO,
                                          PasswordEncoder passwordEncoder) {
        //modelmapper
        Customer customer = new Customer();
        customer.setEmail(customerDTO.getEmail());
        String password =  passwordEncoder.encode(customerDTO.getPassword());
        customer.setPassword(password);
        customer.setName(customerDTO.getName());
        customer.setBirth(customerDTO.getBirth());
        customer.setPhone(customerDTO.getPhone());
        customer.setNickname(customerDTO.getNickname());
        customer.setRole(Role.USER);      // 사용자가 가입했을때

        return customer;
    }


}
