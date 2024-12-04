package com.example.gongbangwa.controller;

import com.example.gongbangwa.dto.AtelierDTO;
import com.example.gongbangwa.dto.CustomerDTO;
import com.example.gongbangwa.dto.PageRequestDTO;
import com.example.gongbangwa.dto.search.AtelierSearchDTO;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.Customer;
import com.example.gongbangwa.service.AtelierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
    public String atelierSignUpP(@Valid AtelierDTO atelierDTO,
                                  BindingResult bindingResult, Model model
            , @RequestParam("atelierImgFile") List<MultipartFile> atelierImgFileList) throws IOException {
        log.info("공방회원 가입 전송 들어옴?");/*회원가입 폼에서 전달한 값 받는 페이지 들어옴*/
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

        return "redirect:/login";/*로그인창으로 갑시다~*/
    }

    /*내 정보 보기*/
    @GetMapping("/myInfo")
    public String myInfo(Principal principal , Model model){

        String email = principal.getName();/*principal이용해서 본인의 이메일을 가져옴*/
        Atelier atelier = atelierService.findByEmail(email);/*유저에 저장*/
        log.info(atelier);
        model.addAttribute("atelier", atelier);/*atelier라는 이름으로 user값 전송*/


        return "/atelier/myInfo";/*마이페이지로 이동~*/
    }

    //남들도 보는 페이지
    @GetMapping("/read")
    public String atelierRead(AtelierDTO atelierDTO, Model model){

        model.addAttribute("atelierDTO", atelierService.atelierRead(atelierDTO));

        return "/atelier/read";
    }

    //  내 정보 수정
    @GetMapping("/modify")/*수정 창 진입*/
    public String getmyinfo(Principal principal, Model model) {
        String email = principal.getName();/*principal로 본인의 이메일을저장*/
        Atelier atelier = atelierService.findByEmail(email);/*유저에 저장*/
        model.addAttribute("atelier", atelier);/*user라는 이름으로 user값 전송*/
        return "/atelier/modify";/*수정창으로 이동*/
    }

    //  수정된 값 받아오는곳
    @PostMapping("/modify")
    public String modify(@Valid Principal principal, AtelierDTO atelierDTO, Model model) {
        String email = principal.getName();

        log.info(atelierDTO);
        try {
            atelierService.updateAtelier(email, atelierDTO, passwordEncoder);    //3개의 값을 담아서 updateUser로 보내줌
//      userDTO: 사용자 정보를 담고 있는 데이터 전송 객체
//      업데이트할 사용자의 '새로운' 정보를 포함 함
        } catch (IllegalStateException e) {         //예외처리 일치화
            model.addAttribute("errorMessage", e.getMessage());
            return "/atelier/modify";
        }
//        model.addAttribute("user", userDTO); // dto가 기존의 값을 저장하고있음
        return "redirect:/atelier/myInfo"; //수정된값을 가지고 바로 info페이지로 돌아감
    }


    //목록
    @GetMapping({"/list","/list/{page}"})
    public String questList(AtelierSearchDTO atelierSearchDTO, PageRequestDTO pageRequestDTO,
                            @PathVariable("page") Optional<Integer> page, Model model, Principal principal) {


//        if (principal != null) {
//            String nickname = mainService.getUserName(principal);
//            model.addAttribute("nickname", nickname);
//
//            List<LikeDetailDTO> likeDetailDTOList
//                    = likeService.getLikeList(principal.getName());
//            // email을 파라미터로 넘긴다.
//
//            log.info(likeDetailDTOList);
//
//            model.addAttribute("likeDetailDTOList", likeDetailDTOList);
//        }

        Pageable pageable = PageRequest
                .of(page.isPresent() ? page.get() : 0 , 9);

        Page<AtelierDTO> ateliers = atelierService
                .getAtelierPage(atelierSearchDTO, pageable);

//        quests.forEach(quest -> log.info(quest));

        model.addAttribute("ateliers", ateliers);
        model.addAttribute("maxPage", 10);


        return "/atelier/list";
    }




}
