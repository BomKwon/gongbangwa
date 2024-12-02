package com.example.gongbangwa.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String main() {
        return "/main";
    }

    @GetMapping("/signup")
    public String selectRegi() {
        return "/user/select";
    }

    @GetMapping("/login")
    public String loginUser(){

        return "/user/login";
    }
    @GetMapping("/login/error")
    public String loginError(Model model){

        model.addAttribute("errorMessage", "이메일 또는 비밀번호가 틀렸습니다!!!!");

        return "/customer/login";
    }

    @GetMapping("/FAQs")
    public String FAQs(){
        //자주묻는질문

        return "/other/faq";
    }

    @GetMapping("/about")
    public String about(){
        //사이트 설명

        return "/other/about";
    }

    @GetMapping("/developer_Info")
    public String developerInfo(){
        //개발자 설명

        return "/other/developer";
    }


}
