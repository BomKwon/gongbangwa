package com.example.gongbangwa.service;
import com.example.gongbangwa.dto.CustomerDTO;
import com.example.gongbangwa.dto.PageRequestDTO;
import com.example.gongbangwa.dto.PageResponseDTO;
import com.example.gongbangwa.dto.PasswordDTO;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.Customer;
import com.example.gongbangwa.repository.AtelierRepository;
import com.example.gongbangwa.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AtelierRepository atelierRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


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


    public String getCustomer(Principal principal){

        String email = principal.getName();
        Customer customer = this.findByEmail(email);

        String nickname = customer.getNickname();

        return nickname;
    }


    //정보수정
    public void updateCustomer(String email, CustomerDTO customerDTO, PasswordEncoder passwordEncoder) {    //페스워드엔코딩 받아주기
        Customer customer = customerRepository.findByEmail(email);   //데이터베이스에서 주어진 이메일과 일치하는 사용자 정보를 가져와서 customer 변수에 넣음
        if (customer == null) {
            throw new IllegalStateException("사용자를 찾을 수 없습니다. " + email);
        }  if (customer != null) {
            customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        }
        customer.setName(customerDTO.getName());
        customer.setPhone(customerDTO.getPhone());
        customer.setNickname(customerDTO.getNickname());
        log.info(customer);
    }
    public void updatepw(String email,  String newPassword, PasswordEncoder passwordEncoder) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new IllegalStateException("사용자를 찾을 수 없습니다. " + email);
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodedPassword);

        // 변경된 사용자 정보를 데이터베이스에 저장
        customerRepository.save(customer);

        log.info("비밀번호가 변경된 사용자 정보: {}", customer);
    }


    //유저 리스트
    public PageResponseDTO<CustomerDTO> list(PageRequestDTO pageRequestDTO) {
        Page<Customer> customerPage = customerRepository.findAll(pageRequestDTO.getPageable());
        //customerPage에 customerReposigtory를 사용해서 pageRequestDTO 안의 Pageable에서 모든걸 찾아온걸 담아줌(customer배열타입)
        List<CustomerDTO> customerdDTOList =
                customerPage.getContent().stream()
                        .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                        .collect(Collectors.toList());

        log.info(customerdDTOList); //boardDTOList에는 bno등은 있지만 customerId는 없다. customer.customerId라서


        return PageResponseDTO.<CustomerDTO>withAll()
                .dtoList(customerdDTOList)
                .pageRequestDTO(pageRequestDTO)
                .total((int) customerPage.getTotalElements())
                .build();
    }
    public Customer findEmail(String name){
        return customerRepository.findByName(name);
    }
    public Customer findPw(String  name,String email){
        return customerRepository.findByNameAndEmail(name,email);
    }

    public Customer checkCustomer(String name, String email){
        Customer customer =
                customerRepository.findByNameAndEmail(name, email);

        if (customer == null){
            return null;
        }



        return customer;

    }

    public void changePw (PasswordDTO passwordDTO){

        log.info("service에서 받은 DTO 확인 : " + passwordDTO);
        Customer customer =  customerRepository.findByEmail(passwordDTO.getEmail());
        log.info("검색한 customer값 : " + customer);
        if(customer !=null && customer.getName().equals(passwordDTO.getName())){
            customer.setPassword(passwordEncoder.encode(passwordDTO.getPassword2()));
        }

        log.info("customer 확인 : " + customer);

    }

    public String remove(int cno) {

        Customer customer = customerRepository
                .findById(cno)
                .orElseThrow(EntityNotFoundException::new);
        String nickname = customer.getNickname();
        customerRepository.deleteById(cno);

        return nickname;

    }



}
