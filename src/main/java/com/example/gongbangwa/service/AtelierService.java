package com.example.gongbangwa.service;
import com.example.gongbangwa.dto.AtelierDTO;
import com.example.gongbangwa.dto.AtelierImgDTO;
import com.example.gongbangwa.dto.CustomerDTO;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.AtelierImg;
import com.example.gongbangwa.entity.Customer;
import com.example.gongbangwa.repository.AtelierImgRepository;
import com.example.gongbangwa.repository.AtelierRepository;
import com.example.gongbangwa.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AtelierService {


    private final AtelierRepository atelierRepository;
    private final AtelierImgService atelierImgService;
    private final AtelierImgRepository atelierImgRepository;

    //이메일 중복 검사용
    private final CustomerRepository customerRepository;

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    //회원정보찾기 이메일로
    public Atelier findByEmail(String email) {

        return atelierRepository.findByEmail(email);
    }

    private void validateDuplicate(Atelier atelier) {
        log.info("이미 가입된 회원인지 확인");
        // 데이터베이스에 저장된 회원가입이 되어있는지 찾아본다.
        Atelier findMemberA = atelierRepository.findByEmail(atelier.getEmail());
        Customer findMemberC = customerRepository.findByEmail(atelier.getEmail());

        //이미 가입된 email이라면
        if(findMemberA != null || findMemberC != null) {
            log.info("이미 가입된 이메일");
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }

    }

    //상세정보(전체공개용)
    public AtelierDTO atelierRead(AtelierDTO atelierDTO){
        Atelier atelier = atelierRepository.findById(atelierDTO.getAno()).orElseThrow();
        AtelierDTO dto = modelMapper.map(atelier,AtelierDTO.class);

        List<AtelierImgDTO> imgDTOList = atelierImgRepository.findByAtelier_Ano(atelierDTO.getAno())
                .stream().map(gameImg ->
                        modelMapper.map(gameImg,AtelierImgDTO.class))
                .collect(Collectors.toList());

        dto.setAtelierImgDTOList(imgDTOList);

        return dto;
    }


    public Integer saveAterlier(Atelier atelier,
                                List<MultipartFile> multipartFiles) throws Exception{

        log.info("사용자가 있는지 확인하기 전");

        // 사용자가 이미 있는지 찾기
        validateDuplicate(atelier);

        log.info("가입된 사용자가 없어서 저장하기 전");


        //이미지 등록
        for (int i=0; i < multipartFiles.size(); i++){
            //입력받은 아이템이미지 숫자만큼
            //하지만 우리가 만들어놓은건 5개라 5개 들어옴
            AtelierImg atelierImg = new AtelierImg();
            atelierImg.setAtelier(atelier);      //이 아이템은 id를 가지고 있는가 저장되는가? 더티

            if(i == 0 ){
                atelierImg.setRepimgYn("Y");   //대표이미지

            }else {
                atelierImg.setRepimgYn("N");
            }

            atelierImgService.saveAtelierImg(atelierImg, multipartFiles.get(i));

        }
        return atelier.getAno();
    }

    @Transactional
    public int updateViews(int ano) { //조회수

        // TODO: 2024-12-03 자기자신이 보면 조회수가 늘어나지 않도록 추후 추가하기

        return atelierRepository.updateAtelierViews(ano);
    }

    public String getAtelier(Principal principal){

        String email = principal.getName();
        Atelier atelier = atelierRepository.findByEmail(email);

        String atelierNm = atelier.getAtelierNm();

        return atelierNm;
    }

    //상세정보


    //정보수정
    public void updateAtelier(String email, AtelierDTO atelierDTO, PasswordEncoder passwordEncoder) {    //페스워드엔코딩 받아주기
        Atelier atelier = atelierRepository.findByEmail(email);   //데이터베이스에서 주어진 이메일과 일치하는 사용자 정보를 가져와서 customer 변수에 넣음
        if (atelier == null) {
            throw new IllegalStateException("사용자를 찾을 수 없습니다. " + email);
        }  if (atelier != null) {
            atelier.setPassword(passwordEncoder.encode(atelierDTO.getPassword()));
        }
        atelier.setName(atelierDTO.getName());
        atelier.setPhone(atelierDTO.getPhone());
        atelier.setAtelierAdd(atelierDTO.getAtelierAdd());
        atelier.setAtelierNm(atelierDTO.getAtelierNm());
        atelier.setAtelierDetail(atelierDTO.getAtelierDetail());
        log.info(atelier);
    }

//    public void updatepw(String email,  String newPassword, PasswordEncoder passwordEncoder) {
//        Customer customer = customerRepository.findByEmail(email);
//        if (customer == null) {
//            throw new IllegalStateException("사용자를 찾을 수 없습니다. " + email);
//        }
//
//        String encodedPassword = passwordEncoder.encode(newPassword);
//        customer.setPassword(encodedPassword);
//
//        // 변경된 사용자 정보를 데이터베이스에 저장
//        customerRepository.save(customer);
//
//        log.info("비밀번호가 변경된 사용자 정보: {}", customer);
//    }





}
