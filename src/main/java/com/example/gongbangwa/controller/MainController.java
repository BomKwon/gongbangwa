package com.example.gongbangwa.controller;

import com.example.gongbangwa.service.MemberuserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MainController {

    private final MemberuserService memberuserService;
    private final PasswordEncoder passwordEncoder;

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

    @GetMapping("/session")
    public String loginSession(Principal principal, HttpServletRequest httpServletRequest){
        try {
            HttpSession session = httpServletRequest.getSession(true);
            log.info(session + " get nickName 전");
            // principal.getName는   내부적으로 costom.getName() 를 사용  //auth2에 getname

            session.setAttribute("nickName", memberuserService.readMemberuser(principal.getName()).getNickname());
            session.setAttribute("role", memberuserService.readMemberuser(principal.getName()).getRole().name());

            log.info(session + "메인컨트롤러 세션!!");
            log.info(principal.getName() + "메인컨트롤러 이름뜨나!!");
        } catch (UsernameNotFoundException e) {
            log.info("유저정보 읽기 실패");
            return "null";
        }
        return "redirect:/";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){

        model.addAttribute("errorMessage", "이메일 또는 비밀번호가 틀렸습니다!!!!");

        return "/user/login";
    }


    @GetMapping("/map")
    public void map(Model model){

    }




//    @GetMapping("/findid")
//    public String userfindid(){
//        log.info("헤이");
//        return "/user/findid";
//    }
//    @PostMapping("/findid")
//    public String userfindid(String name,Model model){
//        User findemail = userService.findemail(name);
//        if (findemail == null){
//            model.addAttribute("loginId","없음");
//            log.info("여기좀 오자");
//            return "/user/findid";
//        }
//        model.addAttribute("loginEmail",findemail.getEmail());
//        log.info("아니다 여기로 오자");
//
//        return "/user/findid";
//    }
//
//    @GetMapping("/findpw")
//    public String findPWGet(Principal principal, RedirectAttributes redirectAttributes, String email, String name, Model model){
//
//        User findpw = userService.findpw(name,email);
//        if (principal != null){
//            model.addAttribute("loginEmail",findpw.getClass());
//            redirectAttributes.addFlashAttribute("result", "이미 로그인 되었습니다.");
//            return "redirect:/";
//        }else {
//
//            return "/user/findpw";
//        }
//
//
//    }
//
//    @ResponseBody
//    @PostMapping("/findpw")
//    public String findPWPost(@RequestBody User user, Model model){
//
//        //1. 이름, 이메일, 아이디를 입력받는다.
//        log.info("받은 User : " + user);
//
//        //2. 입력받은 정보로 회원이 있는지 확인한다.
//        User user1 = userService.checkUser(user.getName(), user.getEmail());
//
//        //3-1. 회원이 없다면 오류 메시지를 출력한다. (종료) //여기까지는 할 수 있을 거 같음
//
//        if (user1 == null){
//            model.addAttribute("result", "등록된 회원이 존재하지 않습니다.");
//            return "aaa";
//        }
//
//        return "bbb";
//
//        //3-2. 회원이 있다면 '같은 페이지'에서 다시 비밀번호를 입력받는다.
//        //4-1. 두 비밀번호가 같고 길이가 8~20자라면 변경. 완료 메시지 출력.
//        //4-2. 비밀번호 조건이 충족되지 않으면 오류 메시지 출력.
//
//    }
//
//    @ResponseBody
//    @PostMapping("/findpw1")
//    public String changePw (@RequestBody PasswordDTO passwordDTO,User user){
//        userRepository.findByEmail(user.getPassword());
//
//
//
//        log.info("받은 UserDTO : "+passwordDTO.getEmail() );
//        log.info("패스워드 DTO" + passwordDTO.getPassword2()+"그리고"+passwordDTO.getConpassword()+ "마지막" + passwordDTO.getPassword());
//
//        //비밀번호 일치 여부 확인
//        if (passwordDTO.getPassword2().equals(passwordDTO.getConpassword())
//                && passwordDTO.getPassword2().length() >= 8
//                && passwordDTO.getPassword2().length() <= 20){
//
//            userService.changePw(passwordDTO); // 비밀번호 변경
//
//            return "bbb";
//        } else if (!passwordDTO.getPassword2().equals(passwordDTO.getConpassword())) {
//            log.info("야 여기 왔다 ㅋㅋ");
//
//
//
//
//            return "ccc";
//        }
//
//
//        return "aaa";
//
//
//
//    }

//    @GetMapping("/resigncheck")
//    public String resigncheck(){
//
//        return "/user/resigncheck";
//    }
//    @Transactional
//    @GetMapping("/resign")
//    public String userresign(Principal principal, UserDTO userDTO){
//        User user = userService.findByEmail(principal.getName());
//
//        if (user != null && user.getUserRole().name().equals("ADMIN") && userDTO.getEmail() != null) {
//
//            cartGameRepository.deleteByCart_User_Email(principal.getName());
//
//            orderGameRepository.deleteByOrder_User_Email(principal.getName());
//
//            cartRepository.deleteByUser_Email(principal.getName());
//
//            orderRepository.deleteByUser_Email(principal.getName());
//
//            reviewRepository.deleteByWriter(principal.getName());
//
//            replyRepository.deleteByRpWriter(principal.getName());
//
//
//            userRepository.deleteUserByEmail(userDTO.getEmail());
//            return "redirect:/user/list";
//
//        } else {
//            cartGameRepository.deleteByCart_User_Email(principal.getName());
//
//            orderGameRepository.deleteByOrder_User_Email(principal.getName());
//
//            cartRepository.deleteByUser_Email(principal.getName());
//
//            orderRepository.deleteByUser_Email(principal.getName());
//
//            reviewRepository.deleteByWriter(principal.getName());
//
//            replyRepository.deleteByRpWriter(principal.getName());
//
//            userRepository.deleteUserByEmail(principal.getName());
//            return "redirect:/user/logout";
//        }
//
//
//
//    }


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
