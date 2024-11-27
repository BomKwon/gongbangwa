package com.example.gongbangwa.controller;

import com.example.gongbangwa.service.AtelierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/atelier")
public class AtelierController {

    AtelierService atelierService;

    //공방 회원 가입
    @GetMapping("/signup")
    public String register() {
        return "/atelier/signup";
    }


}
