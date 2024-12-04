package com.example.gongbangwa.service;
import com.example.gongbangwa.dto.AtelierDTO;
import com.example.gongbangwa.dto.AtelierImgDTO;
import com.example.gongbangwa.dto.search.AtelierSearchDTO;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.AtelierImg;
import com.example.gongbangwa.entity.Memberuser;
import com.example.gongbangwa.repository.AtelierImgRepository;
import com.example.gongbangwa.repository.AtelierRepository;
import com.example.gongbangwa.repository.MemberuserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
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
    private final MemberuserRepository memberuserRepository;

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;



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

    //상세정보
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public AtelierDTO getQuestDetail(int ano){ //pk
        //atelierImgRepository.findByAino(ano); 이거면된다.
        //하지만 필요에 의해서 정렬된 값으로 가져오고 싶어요 그러면 repository에서
        //쿼리메소드로 추가로 만들면 된다.


        //1. 아이디로 이미지가져오기
        List<AtelierImg> atelierImgList =
                atelierImgRepository.findByAtelier_Ano(ano);

        //2. 이미지 entity list를 dto list로 변환
        List<AtelierImgDTO> atelierImgDTOList = new ArrayList<>();
//        private static ModelMapper modelMapper = new ModelMapper();
        for(AtelierImg atelierImg : atelierImgList){

//            ItemImgDto itemImgDto =  modelMapper.map(itemImg, ItemImgDto.class);
            //modelmapper
            AtelierImgDTO atelierImgDTO = AtelierImgDTO.of(atelierImg);
            atelierImgDTOList.add(atelierImgDTO);
        }
        //3. 상품 아이디로 상품정보 가져오고
        Atelier atelier = atelierRepository.findById(ano).orElseThrow(EntityNotFoundException::new);
        //select * from item  where item_id = :item_id

        //4. 상품정보를 dto로 변환
        AtelierDTO atelierDTO = AtelierDTO.of(atelier);

        //5. 상품정보dto 에 이미지들을 set!!
        atelierDTO.setAtelierImgDTOList(atelierImgDTOList);

        return atelierDTO;

    }


    //상품정보 업데이트
    public Integer updateAtelier (AtelierDTO atelierDTO,
                             List<MultipartFile> multipartFiles) throws Exception {

        //상품정보가져오기
        Atelier atelier = atelierRepository.findById(atelierDTO.getAno())
                .orElseThrow(EntityNotFoundException::new);
        //상품정보 업데이트  repository.save(item) 이것 수정이다 >
        // 영속성 상태일때는 변경감지를 이용 update > 트랜잭션이 종료될때
        atelier.updateAtelier(atelierDTO);

        List<Integer> ainos = atelierDTO.getAtelierImgIds();


        //이미지 등록
        for (int i = 0; i < multipartFiles.size(); i++) {

            atelierImgService
                    .updateQuestImg(ainos.get(i),multipartFiles.get(i) );

        }
        return atelier.getAno();
    }



    //목록
    @Transactional
    public Page<AtelierDTO> getAtelierPage(AtelierSearchDTO atelierSearchDTO, Pageable pageable){

        return atelierRepository.getMainAtelierPage(atelierSearchDTO, pageable);
    }


    //삭제
    public String remove(int ano) {


        Atelier atelier
                = atelierRepository
                .findById(ano)
                .orElseThrow(EntityNotFoundException::new);
        String atelierNm = atelier.getAtelierNm();
        atelierRepository.deleteById(ano);


        return atelierNm;

    }


}
