package com.example.gongbangwa.controller;

import com.example.gongbangwa.dto.CustomerDTO;
import com.example.gongbangwa.dto.PageRequestDTO;
import com.example.gongbangwa.dto.PageResponseDTO;
import com.example.gongbangwa.dto.PasswordDTO;
import com.example.gongbangwa.entity.Customer;
import com.example.gongbangwa.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

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

        model.addAttribute("customerDTO",new CustomerDTO());
        /*새로운 CustomerDTO를 customerDTO로 만들어서 보내줌*/
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

            return "/user/signup";/*얘도 돌아감*/
        }
        log.info("야앙아아아아아아아아아");/*완료~*/
        model.addAttribute("result", "회원가입을 축하드립니다!");
        return "redirect:/login";/*로그인창으로 갑시다~*/
    }

    //관리자 회원 가입
    @GetMapping("/signupAdmin")
    public String adminSignUp(Model model) {

        model.addAttribute("customerDTO",new CustomerDTO());
        /*새로운 CustomerDTO를 customerDTO로 만들어서 보내줌*/
        log.info("관리자회원가입이다!!!!!!");
        /*회원가입창 들어왔음*/
        return "/user/adminSignup";
    }

    /*html파일에서 post로 보내준 값 받음*/
//    지금 뭔 오류있어서 안댐
//    @PostMapping("/signupAdmin")
//    public String adminSignUpP(@Valid CustomerDTO customerDTO,
//                                  BindingResult bindingResult, Model model){
//        log.info("일반회원 가입 전송 들어옴?");/*회원가입 폼에서 전달한 값 받는 페이지 들어옴*/
//        if (bindingResult.hasErrors()){ /*유효성검사*/
//            log.info(bindingResult.getAllErrors());
//            return "/user/adminSignup";/*문제생기면 다시 get으로 돌아감*/
//        }
//        try {
//            Customer customer = Customer.createCustomer(customerDTO,passwordEncoder);
//            customerService.saveCustomer(customer);/*회원생성 */
//        }catch (IllegalStateException e){
//            model.addAttribute("errorMessage",e.getMessage());
//            /*중복시 에러창 errorMessage라는 이름으로 값보내줌*/
//
//            return "/user/adminSignup";/*얘도 돌아감*/
//        }
//        log.info("야앙아아아아아아아아아");/*완료~*/
//        model.addAttribute("result", "회원가입을 축하드립니다!");
//        return "redirect:/login";/*로그인창으로 갑시다~*/
//    }


    /*내 정보 보기*/
    @GetMapping("/myInfo")
    public String myInfo(Principal principal , Model model){

        String email = principal.getName();/*principal이용해서 본인의 이메일을 가져옴*/
        Customer customer = customerService.findByEmail(email);/*유저에 저장*/
        log.info(customer);
        model.addAttribute("customer", customer);/*customer라는 이름으로 user값 전송*/


        return "/customer/myInfo";/*마이페이지로 이동~*/
    }

    //  내 정보 수정
    @GetMapping("/modify")/*수정 창 진입*/
    public String getmyinfo(Principal principal, Model model) {
        String email = principal.getName();/*principal로 본인의 이메일을저장*/
        Customer customer = customerService.findByEmail(email);/*유저에 저장*/
        model.addAttribute("customer", customer);/*user라는 이름으로 user값 전송*/
        return "/customer/modify";/*수정창으로 이동*/
    }

    //  수정된 값 받아오는곳
    @PostMapping("/modify")
    public String modify(@Valid Principal principal, CustomerDTO customerDTO, Model model) {
        String email = principal.getName();

        log.info(customerDTO);
        try {
            customerService.updateCustomer(email, customerDTO, passwordEncoder);    //3개의 값을 담아서 updateUser로 보내줌
//      userDTO: 사용자 정보를 담고 있는 데이터 전송 객체
//      업데이트할 사용자의 '새로운' 정보를 포함 함
        } catch (IllegalStateException e) {         //예외처리 일치화
            model.addAttribute("errorMessage", e.getMessage());
            return "/customer/modify";
        }
//        model.addAttribute("user", userDTO); // dto가 기존의 값을 저장하고있음
        return "redirect:/customer/myInfo"; //수정된값을 가지고 바로 info페이지로 돌아감
    }
    @GetMapping("/modifypw")/*수정 창 진입*/
    public String getpassword(Principal principal, Model model) {
        String email = principal.getName();/*principal로 본인의 이메일을저장*/
        Customer customer = customerService.findByEmail(email);/*유저에 저장*/

        model.addAttribute("customer", customer);/*user라는 이름으로 user값 전송*/
        model.addAttribute("passwordDTO", new PasswordDTO());/*user라는 이름으로 user값 전송*/
        log.info("5");
        return "/customer/modifypw";/*수정창으로 이동*/
    }
    //  수정된 값 받아오는곳
    @PostMapping("/modifypw")
    public String updatepassword(@Valid PasswordDTO passwordDTO,
                                 BindingResult bindingResult,
                                 Model model,
                                 Principal principal) {
        log.info(passwordDTO);
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        model.addAttribute("customer",customer);
        log.info(customer.getPassword());
        log.info("패스워드일치여부"+passwordEncoder.matches(passwordDTO.getPassword(),customer.getPassword()));
        if (!passwordEncoder.matches(passwordDTO.getPassword(),customer.getPassword())) {
            bindingResult.rejectValue("passwordDTO", "passwordDTO", "기존 비밀번호와 다릅니다.");
            return "customer/modifypw";
        }
        if (bindingResult.hasErrors()){
            return "customer/modifypw";
        }
        log.info(passwordEncoder.matches(passwordDTO.getPassword2(),customer.getPassword()));
        if (passwordEncoder.matches(passwordDTO.getPassword2(),customer.getPassword())){
            bindingResult.rejectValue("passwordDTO", "passwordDTO", "기존 비밀번호와 같습니다..");
            return "customer/modifypw";
        }
        if (!passwordDTO.getPassword2().equals(passwordDTO.getConpassword())){
            bindingResult.rejectValue("passwordDTO","passwordDTO","비밀번호가 일치하지 않습니다");
            return "customer/modifypw";
        }
        log.info("1");
        // 3. 현재 로그인한 사용자의 이메일을 가져옴

        // 4. 비밀번호 변경을 위한 서비스 호출
        try {
            customerService.updatepw(email,passwordDTO.getPassword2(), passwordEncoder);

            log.info("3");
//      업데이트할 사용자의 '새로운' 정보를 포함 함
        } catch (IllegalStateException e) {         //예외처리 일치화
            model.addAttribute("errorMessage", e.getMessage());
            return "customer/modifypw";
        }
        log.info("4");
//        model.addAttribute("user", userDTO); // dto가 기존의 값을 저장하고있음
        return "redirect:/customer/modify"; //수정된값을 가지고 바로 info페이지로 돌아감

    }




//
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


}
