package com.example.gongbangwa.controller;

import com.example.gongbangwa.dto.AtelierDTO;
import com.example.gongbangwa.dto.PageRequestDTO;
import com.example.gongbangwa.dto.search.AtelierSearchDTO;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.service.AtelierService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping("/master/register")
    public String register(Model model) {

        model.addAttribute("atelierDTO",new AtelierDTO());
        /*새로운 AtelierDTO를 userDTO라는 이름으로 만들어서 보내줌*/

        log.info("사장회원가입이다!!!!!!");

        /*회원가입창 들어왔음*/

        return "/atelier/register";
    }

    /*html파일에서 post로 보내준 값 받음*/
    @PostMapping("/master/register")
    public String atelierSignUpP(@Valid AtelierDTO atelierDTO,
                                  BindingResult bindingResult, Model model
            , @RequestParam("atelierImgFile") List<MultipartFile> atelierImgFileList) throws IOException {
        log.info("공방회원 가입 전송 들어옴?");/*회원가입 폼에서 전달한 값 받는 페이지 들어옴*/
        if (bindingResult.hasErrors()){ /*유효성검사*/
            log.info(bindingResult.getAllErrors());

            return "/atelier/register";/*문제생기면 다시 get으로 돌아감*/
        }
        if (atelierImgFileList.get(0).isEmpty() && atelierDTO.getAno() == null) {

            //대표이미지는 꼭 삽입해야한다.
            model.addAttribute("errorMessage", "대표사진은 필수항목입니다!!!");
            return "/atelier/register";
        }
        try {
            Atelier atelier = Atelier.createAtelier(atelierDTO);
            atelierService.saveAterlier(atelier, atelierImgFileList);/*회원생성 */
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage",e.getMessage());
            /*중복시 에러창 errorMessage라는 이름으로 값보내줌*/

            return "/atelier/register";/*얘도 돌아감*/
        }
        log.info("야앙아아아아아아아아아");/*완료~*/

        model.addAttribute("result", "회원가입을 축하드립니다!");

        return "redirect:/atelier/master/list";/*공방주인 전용 리스트로 가용~*/
    }


//    //  내 정보 수정
//    @GetMapping("/master/modify")/*수정 창 진입*/
//    public String getModify(AtelierDTO atelierDTO, Model model) {
//        model.addAttribute("atelierDTO", atelierDTO);/*user라는 이름으로 user값 전송*/
//        return "/atelier/modify";/*수정창으로 이동*/
//    }
//
//    //  수정된 값 받아오는곳
//    @PostMapping("/master/modify")
//    public String modify(@Valid AtelierDTO atelierDTO, BindingResult bindingResult,
//                         @RequestParam("questImgFile") List<MultipartFile> multipartFiles,
//                         Model model) throws Exception {
//
//        log.warn(atelierDTO);
//
//        try {
//            atelierService.updateAtelier(atelierDTO, multipartFiles);
//        }catch (Exception e) {
//            model.addAttribute("atelierDTO", atelierService.atelierRead(atelierDTO));
//            log.warn(bindingResult.getFieldError());
//            return "/atelier/modify";
//        }
//
//        return "redirect:/atelier/read"; //수정된값을 가지고 바로 read로 돌아감
//    }

    @GetMapping("/master/modify/{ano}")        //   /quest/3  3번이미지 보여줘
    public String atelierModifyGet(@PathVariable("ano") int ano
            , Model model, Principal principal) {

        try {

            AtelierDTO atelierDTO = atelierService.getQuestDetail(ano);
            model.addAttribute("atelierDTO" , atelierDTO);
            // html에서 thyleaf  th:object="${questFormDto}"

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage",
                    "존재하지 않는 공방입니다요");

            return "/atelier/list";

        }

        return "/atelier/modify";

    }

    // TODO: 2024-07-09 수정필요함 사진인식안됨
    @PostMapping("/master/modify{ano}")
    public String atelierUpdate(@Valid AtelierDTO atelierDTO,
                              BindingResult bindingResult, Principal principal,
                              @RequestParam("atelierImgFile") List<MultipartFile> multipartFiles,
                              Model model) {

        if (bindingResult.hasErrors()) {
            return "/atelier/modify";
        }

        if(multipartFiles.get(0).isEmpty() && atelierDTO.getAno() == null){
            model.addAttribute("errorMessage", "대표 이미지는 필수 입력값입니다.");
            return "/atelier/modify";
        }

        try {
            atelierService.updateAtelier(atelierDTO, multipartFiles);
        }catch (Exception e){
            model.addAttribute("errorMessage", "수정 중에 문제가 발생했습니다.");
            return "/quest/questForm";
        }
        return "redirect:/";

    }



    //목록
    @GetMapping({"/master/list","/master/list/{page}"})
    public String atelierMasterList(AtelierSearchDTO atelierSearchDTO, PageRequestDTO pageRequestDTO,
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


        return "/atelier/listMaster";
    }


    @GetMapping("/master/remove/{ano}")
    public @ResponseBody ResponseEntity questRemove(@PathVariable("ano") int ano, RedirectAttributes redirectAttributes,
                                                    Principal principal, Model model){

        log.info("들어온 아이디 : " + ano);


        String atelier = atelierService.remove(ano);
        redirectAttributes.addFlashAttribute("result", ano + "번 공방이 삭제 완료 되었습니다 ㅜ.ㅜ");

        return new ResponseEntity<String>(atelier, HttpStatus.OK);

    }




    //목록
    @GetMapping({"/list","/list/{page}"})
    public String atelierList(AtelierSearchDTO atelierSearchDTO, PageRequestDTO pageRequestDTO,
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

    //상세보기
    @GetMapping("/read")
    public String atelierRead(AtelierDTO atelierDTO, Model model){

        model.addAttribute("atelierDTO", atelierService.atelierRead(atelierDTO));

        return "/atelier/read";
    }



}
