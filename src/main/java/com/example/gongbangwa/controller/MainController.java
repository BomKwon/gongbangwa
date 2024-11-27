package com.example.gongbangwa.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String main() {
        return "/main";
    }

    @GetMapping("/signin")
    public String login() {
        return "/user/login";
    }

    @GetMapping("/signup")
    public String selectRegi() {
        return "/user/select";
    }


}
