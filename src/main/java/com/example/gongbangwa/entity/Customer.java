package com.example.gongbangwa.entity;

import com.example.gongbangwa.constant.Role;
import com.example.gongbangwa.dto.CustomerDTO;
import com.example.gongbangwa.entity.base.Base;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "customer")
public class Customer extends Base{ // UserDetails를 상속받아 인증 객체로 사용

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;            //회원번호

    @Column(unique = true)
    private String email;       //이메일

    @Column(nullable = false, length = 100)
    private String password;    //비밀번호

    @Column
    private String name;       //이름

    @Column
    private String phone;       //전화번호

    @Enumerated(EnumType.STRING)
    private Role role;          //권한

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
        customer.setRole(customerDTO.getRole());      // 사용자가 가입했을때

        return customer;
    }

}
