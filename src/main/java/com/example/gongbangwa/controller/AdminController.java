package com.example.gongbangwa.controller;

import com.example.gongbangwa.dto.CustomerDTO;
import com.example.gongbangwa.dto.PageRequestDTO;
import com.example.gongbangwa.dto.PageResponseDTO;
import com.example.gongbangwa.entity.Customer;
import com.example.gongbangwa.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {


    private final CustomerService customerService;

    //회원리스트
    @GetMapping("/user/list")
    public String userlist(PageRequestDTO pageRequestDTO, Model model, Principal principal, Customer customer) {


        if(principal == null){
            return "redirect:/";
        }
        log.info(principal.getName());

        if(!customerService.findByEmail(principal.getName()).getRole().toString().equals("ADMIN")){
            return "redirect:/";
        }

        PageResponseDTO<CustomerDTO> responseDTO = customerService.list(pageRequestDTO);
        model.addAttribute("pageResponseDTO", responseDTO);

        return "/user/list";
    }

    //유저 삭제
    @GetMapping("/user/remove/{cno}")
    public @ResponseBody ResponseEntity userRemove(@PathVariable("cno") int cno, RedirectAttributes redirectAttributes,
                                                   Principal principal, Model model){

        log.info("들어온 아이디 : " + cno);

//        String nickname = mainService.getUserName(principal);
//        model.addAttribute("nickname", nickname);

        String customer = customerService.remove(cno);
        redirectAttributes.addFlashAttribute("result", cno + "번 회원이 탈퇴되었습니다.");

        return new ResponseEntity<String>(customer, HttpStatus.OK);

    }
}
