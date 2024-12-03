package com.example.gongbangwa.service;

import com.example.gongbangwa.constant.Role;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.Customer;
import com.example.gongbangwa.repository.AtelierRepository;
import com.example.gongbangwa.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class UserService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final AtelierRepository atelierRepository;

    /*회원 로그인 - 공방사장 + 일반회원 + 관리자 통합*/
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByEmail(email);
        Atelier atelier = atelierRepository.findByEmail(email);

        if(customer == null){
            if(atelier == null){
                throw new UsernameNotFoundException("사용자를 찾을 수 없습니다." + email);
            }else {
                log.info(atelier);
                log.info("현재 로그인하신분의 권한 : " + atelier.getRole().name());
                String role  = "";
                if("ADMIN".equals(atelier.getRole().name())){   //auth 컬럼을 추가로 지정해서 사용
                    log.info("관리자");
                    role = Role.ADMIN.name();
                }else if("MASTER".equals(atelier.getRole().name())){   //auth 컬럼을 추가로 지정해서 사용
                    log.info("맛스타");
                    role = Role.MASTER.name();
                }else {
                    log.info("일반유저");
                    role = Role.USER.name();
                }
                return org.springframework.security.core.userdetails.User.builder()
                        .username( atelier.getEmail() )
                        .password( atelier.getPassword())
                        .roles(role)
                        .build();
            }


        }else {
            log.info(customer);
            log.info("현재 로그인하신분의 권한 : " + customer.getRole().name());
            String role  = "";
            if("ADMIN".equals(customer.getRole().name())){   //auth 컬럼을 추가로 지정해서 사용
                log.info("관리자");
                role = Role.ADMIN.name();
            }else if("MASTER".equals(customer.getRole().name())){   //auth 컬럼을 추가로 지정해서 사용
                log.info("맛스타");
                role = Role.MASTER.name();
            }else {
                log.info("일반유저");
                role = Role.USER.name();
            }
            return org.springframework.security.core.userdetails.User.builder()
                    .username( customer.getEmail() )
                    .password( customer.getPassword())
                    .roles(role)
                    .build();
        }



    }








}
