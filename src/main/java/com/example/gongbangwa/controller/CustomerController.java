package com.example.gongbangwa.controller;

import com.example.gongbangwa.dto.CustomerDTO;
import com.example.gongbangwa.entity.Customer;
import com.example.gongbangwa.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    //공방 회원 가입
    @GetMapping("/signup")
    public String customerSignUp(Model model) {

        model.addAttribute("userDTO",new CustomerDTO());
        /*새로운 CustomerDTO를 userDTO라는 이름으로 만들어서 보내줌
          ㄴ atelier 가입에서도 써야하기 때문*/
        log.info("일반회원가입이다!!!!!!");
        /*회원가입창 들어왔음*/
        return "/customer/signup";
    }

    /*html파일에서 post로 보내준 값 받음*/
    @PostMapping("/signup")
    public String customerSignUpP(@Valid CustomerDTO customerDTO,
                               BindingResult bindingResult, Model model){
        log.info("일반회원 가입 전송 들어옴?");/*회원가입 폼에서 전달한 값 받는 페이지 들어옴*/
        if (bindingResult.hasErrors()){ /*유효성검사*/
            log.info(bindingResult.getAllErrors());
            return "/customer/signup";/*문제생기면 다시 get으로 돌아감*/
        }
        try {
            Customer customer = Customer.createCustomer(customerDTO,passwordEncoder);
            customerService.saveCustomer(customer);/*회원생성 */
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            /*중복시 에러창 errorMessage라는 이름으로 값보내줌*/

            return "/user/register";/*얘도 돌아감*/
        }
        log.info("야앙아아아아아아아아아");/*완료~*/
        return "redirect:/signin";/*로그인창으로 갑시다~*/
    }

}
