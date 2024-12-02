package com.example.gongbangwa.service;
import com.example.gongbangwa.constant.Role;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.Customer;
import com.example.gongbangwa.repository.AtelierRepository;
import com.example.gongbangwa.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AtelierRepository atelierRepository;


    //회원정보찾기 이메일로
    public Customer findByEmail(String email) {

        return customerRepository.findByEmail(email);
    }

    private void validateDuplicate(Customer customer) {
        log.info("이미 가입된 회원인지 확인");
        // 데이터베이스에 저장된 회원가입이 되어있는지 찾아본다.
        Atelier findMemberA = atelierRepository.findByEmail(customer.getEmail());
        Customer findMemberC = customerRepository.findByEmail(customer.getEmail());

        //이미 가입된 email이라면
        if(findMemberA != null || findMemberC != null) {
            log.info("이미 가입된 이메일");
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }

    }

    // 회원가입
    public Customer saveCustomer(Customer customer) {
        log.info("사용자가 있는지 확인하기 전");

        // 사용자가 이미 있는지 찾기
        validateDuplicate(customer);

        log.info("가입된 사용자가 없어서 저장하기 전");

        return customerRepository.save(customer);

    }


    public String getUser(Principal principal){

        String email = principal.getName();
        Customer customer = this.findByEmail(email);

        String nickname = customer.getNickname();

        return nickname;
    }


//    @Transactional(readOnly = true)
//    public CustomerDTO getUserDtl(Long cno){ //pk 상품번호
//
//        //아이디로 유저정보 가져오기
//        Customer customer = customerRepository.findById(cno).orElseThrow(EntityNotFoundException::new);
//        //select * from user  where user_id = :user_id
//        //4. 상품정보를 dto로 변환
//        CustomerDTO customerDTO = CustomerDTO.of(customer);
//
//        return customerDTO;
//
//    }


    //정보 업데이트
//    public Long modifyUser (CustomerDTO customerDTO) throws Exception {
//
//        //정보가져오기 이멜로
//        Customer customer = customerRepository.findById(customerDTO.getCno())
//                .orElseThrow(EntityNotFoundException::new);
//
//        customer.updateUser(userDTO);
//
//        return user.getId();
//    }
//
//    public String remove(Long userId) {
//
//        User user = customerRepository
//                .findById(userId)
//                .orElseThrow(EntityNotFoundException::new);
//        String nickname = user.getNickname();
//        customerRepository.deleteById(userId);
//
//        return nickname;
//
//    }
//
//
//    public Page<Customer> getUserList(UserSearchDTO userSearchDTO, Pageable pageable) {
//        return customerRepository.getUserPage(userSearchDTO, pageable);
//    }



}
