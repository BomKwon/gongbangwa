package com.example.gongbangwa.controller;

import com.example.gongbangwa.dto.AtelierDTO;
import com.example.gongbangwa.dto.CustomerDTO;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.Customer;
import com.example.gongbangwa.service.AtelierService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/atelier")
public class AtelierController {

    private final AtelierService atelierService;
    private final PasswordEncoder passwordEncoder;

    //공방 회원 가입
    @GetMapping("/signup")
    public String register(Model model) {

        model.addAttribute("atelierDTO",new AtelierDTO());
        /*새로운 AtelierDTO를 userDTO라는 이름으로 만들어서 보내줌*/

        log.info("사장회원가입이다!!!!!!");

        /*회원가입창 들어왔음*/

        return "/atelier/signup";
    }

    /*html파일에서 post로 보내준 값 받음*/
    @PostMapping("/signup")
    public String customerSignUpP(@Valid AtelierDTO atelierDTO,
                                  BindingResult bindingResult, Model model
            , @RequestParam("atelierImgFile") List<MultipartFile> atelierImgFileList) throws IOException {
        log.info("일반회원 가입 전송 들어옴?");/*회원가입 폼에서 전달한 값 받는 페이지 들어옴*/
        if (bindingResult.hasErrors()){ /*유효성검사*/
            log.info(bindingResult.getAllErrors());

            return "/atelier/signup";/*문제생기면 다시 get으로 돌아감*/
        }
        if (atelierImgFileList.get(0).isEmpty() && atelierDTO.getAno() == null) {

            //대표이미지는 꼭 삽입해야한다.
            model.addAttribute("errorMessage", "대표사진은 필수항목입니다!!!");
            return "/atelier/signup";
        }
        try {
            Atelier atelier = Atelier.createAtelier(atelierDTO,passwordEncoder);
            atelierService.saveAterlier(atelier, atelierImgFileList);/*회원생성 */
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage",e.getMessage());
            /*중복시 에러창 errorMessage라는 이름으로 값보내줌*/

            return "/atelier/signup";/*얘도 돌아감*/
        }
        log.info("야앙아아아아아아아아아");/*완료~*/

        model.addAttribute("result", "회원가입을 축하드립니다!");

        return "redirect:/atelier/login";/*로그인창으로 갑시다~*/
    }

    @GetMapping("/myInfo")
    public String myInfo(){
        //회원 정보 읽기

        return "/atelier/myInfo";
    }


}
