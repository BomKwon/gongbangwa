package com.example.gongbangwa.controller;

import com.example.gongbangwa.dto.MemberuserDTO;
import com.example.gongbangwa.dto.PasswordDTO;
import com.example.gongbangwa.entity.Memberuser;
import com.example.gongbangwa.service.MemberuserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberuserController {

    private final MemberuserService memberuserService;
    private final PasswordEncoder passwordEncoder;

    //일반회원 가입
    @GetMapping("/signupCustomer")
    public String signUpU(Model model) {

        model.addAttribute("memberuserDTO",new MemberuserDTO());
        /*새로운 memberuserDTO를 memberuserDTO로 만들어서 보내줌*/
        log.info("일반회원가입이다!!!!!!");
        /*회원가입창 들어왔음*/
        return "/user/signupCustomer";
    }

    /*html파일에서 post로 보내준 값 받음*/
    @PostMapping("/signup")
    public String signUpU(@Valid MemberuserDTO memberuserDTO,
                               BindingResult bindingResult, Model model){
        log.info("일반회원 가입 전송 들어옴?");/*회원가입 폼에서 전달한 값 받는 페이지 들어옴*/
        if (bindingResult.hasErrors()){ /*유효성검사*/
            log.info(bindingResult.getAllErrors());
            return "signupCustomer";/*문제생기면 다시 get으로 돌아감*/
        }
        try {
            Memberuser memberuser = Memberuser.createMemeberuser(memberuserDTO,passwordEncoder);
            memberuserService.saveMemberuser(memberuser);/*회원생성 */
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            /*중복시 에러창 errorMessage라는 이름으로 값보내줌*/

            return "signupCustomer";/*얘도 돌아감*/
        }
        log.info("야앙아아아아아아아아아");/*완료~*/
        model.addAttribute("result", "회원가입을 축하드립니다!");
        return "redirect:/login";/*로그인창으로 갑시다~*/
    }

    //사장 회원 가입
    @GetMapping("/signupMaster")
    public String signupM(Model model) {

        model.addAttribute("memberuserDTO",new MemberuserDTO());
        /*새로운 memberuserDTO를 memberuserDTO로 만들어서 보내줌*/
        log.info("관리자회원가입이다!!!!!!");
        /*회원가입창 들어왔음*/
        return "/user/signupMaster";
    }

    @PostMapping("/signupMaster")
    public String signupM(@Valid MemberuserDTO memberuserDTO,
                                    BindingResult bindingResult, Model model){
        log.info("일반회원 가입 전송 들어옴?");/*회원가입 폼에서 전달한 값 받는 페이지 들어옴*/
        if (bindingResult.hasErrors()){ /*유효성검사*/
            log.info(bindingResult.getAllErrors());
            return "signupCustomer";/*문제생기면 다시 get으로 돌아감*/
        }
        try {
            Memberuser memberuser = Memberuser.createMemeberuser(memberuserDTO,passwordEncoder);
            memberuserService.saveMemberuser(memberuser);/*회원생성 */
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            /*중복시 에러창 errorMessage라는 이름으로 값보내줌*/

            return "signupCustomer";/*얘도 돌아감*/
        }
        log.info("야앙아아아아아아아아아");/*완료~*/
        model.addAttribute("result", "회원가입을 축하드립니다!");
        return "redirect:/login";/*로그인창으로 갑시다~*/
    }

    //관리자 회원 가입
    @GetMapping("/signupAdmin")
    public String adminSignUp(Model model) {

        model.addAttribute("memberuserDTO",new MemberuserDTO());
        /*새로운 memberuserDTO를 memberuserDTO로 만들어서 보내줌*/
        log.info("관리자회원가입이다!!!!!!");
        /*회원가입창 들어왔음*/
        return "/user/signupAdmin";
    }

    /*html파일에서 post로 보내준 값 받음*/
    //지금 뭔 오류있어서 안댐
    @PostMapping("/signupAdmin")
    public String adminSignUpP(@Valid MemberuserDTO memberuserDTO,
                                  BindingResult bindingResult, Model model){
        log.info("일반회원 가입 전송 들어옴?");/*회원가입 폼에서 전달한 값 받는 페이지 들어옴*/
        if (bindingResult.hasErrors()){ /*유효성검사*/
            log.info(bindingResult.getAllErrors());
            return "/user/signupAdmin";/*문제생기면 다시 get으로 돌아감*/
        }
        try {
            Memberuser memberuser = Memberuser.createMemeberuser(memberuserDTO,passwordEncoder);
            memberuserService.saveMemberuser(memberuser);/*회원생성 */
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            /*중복시 에러창 errorMessage라는 이름으로 값보내줌*/

            return "/user/signupAdmin";/*얘도 돌아감*/
        }
        log.info("야앙아아아아아아아아아");/*완료~*/
        model.addAttribute("result", "회원가입을 축하드립니다!");
        return "redirect:/login";/*로그인창으로 갑시다~*/
    }


    /*내 정보 보기*/
    @GetMapping("/myInfo")
    public String myInfo(Principal principal , Model model){

        String email = principal.getName();/*principal이용해서 본인의 이메일을 가져옴*/
        Memberuser memberuser = memberuserService.findByEmail(email);/*유저에 저장*/
        log.info(memberuser);
        model.addAttribute("memberuser", memberuser);/*memberuser라는 이름으로 user값 전송*/


        return "/user/myInfo";/*마이페이지로 이동~*/
    }

    //  내 정보 수정
    @GetMapping("/modify")/*수정 창 진입*/
    public String getmyinfo(Principal principal, Model model) {
        String email = principal.getName();/*principal로 본인의 이메일을저장*/
        Memberuser memberuser = memberuserService.findByEmail(email);/*유저에 저장*/
        model.addAttribute("memberuser", memberuser);/*user라는 이름으로 user값 전송*/
        return "/user/modify";/*수정창으로 이동*/
    }

    //  수정된 값 받아오는곳
    @PostMapping("/modify")
    public String modify(@Valid Principal principal, MemberuserDTO memberuserDTO, Model model) {
        String email = principal.getName();

        log.info(memberuserDTO);
        try {
            memberuserService.updateMemberuser(email, memberuserDTO, passwordEncoder);    //3개의 값을 담아서 updateUser로 보내줌
//      userDTO: 사용자 정보를 담고 있는 데이터 전송 객체
//      업데이트할 사용자의 '새로운' 정보를 포함 함
        } catch (IllegalStateException e) {         //예외처리 일치화
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/modify";
        }
//        model.addAttribute("user", userDTO); // dto가 기존의 값을 저장하고있음
        return "redirect:/user/myInfo"; //수정된값을 가지고 바로 info페이지로 돌아감
    }
    @GetMapping("/modifypw")/*수정 창 진입*/
    public String getpassword(Principal principal, Model model) {
        String email = principal.getName();/*principal로 본인의 이메일을저장*/
        Memberuser memberuser = memberuserService.findByEmail(email);/*유저에 저장*/

        model.addAttribute("memberuser", memberuser);/*user라는 이름으로 user값 전송*/
        model.addAttribute("passwordDTO", new PasswordDTO());/*user라는 이름으로 user값 전송*/
        log.info("5");
        return "/user/modifypw";/*수정창으로 이동*/
    }
    //  수정된 값 받아오는곳
    @PostMapping("/modifypw")
    public String updatepassword(@Valid PasswordDTO passwordDTO,
                                 BindingResult bindingResult,
                                 Model model,
                                 Principal principal) {
        log.info(passwordDTO);
        String email = principal.getName();
        Memberuser memberuser = memberuserService.findByEmail(email);
        model.addAttribute("memberuser", memberuser);
        log.info(memberuser.getPassword());
        log.info("패스워드일치여부"+passwordEncoder.matches(passwordDTO.getPassword(), memberuser.getPassword()));
        if (!passwordEncoder.matches(passwordDTO.getPassword(), memberuser.getPassword())) {
            bindingResult.rejectValue("passwordDTO", "passwordDTO", "기존 비밀번호와 다릅니다.");
            return "/user/modifypw";
        }
        if (bindingResult.hasErrors()){
            return "/user/modifypw";
        }
        log.info(passwordEncoder.matches(passwordDTO.getPassword2(), memberuser.getPassword()));
        if (passwordEncoder.matches(passwordDTO.getPassword2(), memberuser.getPassword())){
            bindingResult.rejectValue("passwordDTO", "passwordDTO", "기존 비밀번호와 같습니다..");
            return "/user/modifypw";
        }
        if (!passwordDTO.getPassword2().equals(passwordDTO.getConpassword())){
            bindingResult.rejectValue("passwordDTO","passwordDTO","비밀번호가 일치하지 않습니다");
            return "/user/modifypw";
        }
        log.info("1");
        // 3. 현재 로그인한 사용자의 이메일을 가져옴

        // 4. 비밀번호 변경을 위한 서비스 호출
        try {
            memberuserService.updatepw(email,passwordDTO.getPassword2(), passwordEncoder);

            log.info("3");
//      업데이트할 사용자의 '새로운' 정보를 포함 함
        } catch (IllegalStateException e) {         //예외처리 일치화
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/modifypw";
        }
        log.info("4");
//        model.addAttribute("user", userDTO); // dto가 기존의 값을 저장하고있음
        return "redirect:/user/modify"; //수정된값을 가지고 바로 info페이지로 돌아감

    }





}
