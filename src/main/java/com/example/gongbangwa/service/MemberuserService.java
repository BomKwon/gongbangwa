package com.example.gongbangwa.service;
import com.example.gongbangwa.constant.Role;
import com.example.gongbangwa.dto.MemberuserDTO;
import com.example.gongbangwa.dto.PageRequestDTO;
import com.example.gongbangwa.dto.PageResponseDTO;
import com.example.gongbangwa.dto.PasswordDTO;
import com.example.gongbangwa.entity.Atelier;
import com.example.gongbangwa.entity.Memberuser;
import com.example.gongbangwa.repository.AtelierRepository;
import com.example.gongbangwa.repository.MemberuserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberuserService implements UserDetailsService {

    private final MemberuserRepository memberuserRepository;
    private final AtelierRepository atelierRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    //회원정보찾기 이메일로
    public Memberuser findByEmail(String email) {

        return memberuserRepository.findByEmail(email);
    }

    private void validateDuplicate(Memberuser memberuser) {
        log.info("이미 가입된 회원인지 확인");
        // 데이터베이스에 저장된 회원가입이 되어있는지 찾아본다.
        Memberuser findMember = memberuserRepository.findByEmail(memberuser.getEmail());

        //이미 가입된 email이라면
        if(findMember != null) {
            log.info("이미 가입된 이메일");
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }

    }

    // 회원가입
    public Memberuser saveMemberuser(Memberuser memberuser) {
        log.info("사용자가 있는지 확인하기 전");

        // 사용자가 이미 있는지 찾기
        validateDuplicate(memberuser);

        log.info("가입된 사용자가 없어서 저장하기 전");

        return memberuserRepository.save(memberuser);

    }


    public String getCustomer(Principal principal){

        String email = principal.getName();
        Memberuser memberuser = this.findByEmail(email);

        String nickname = memberuser.getNickname();

        return nickname;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Memberuser memberuser = memberuserRepository.findByEmail(email);

        if(memberuser == null){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다." + email);
        }

        log.info(memberuser);
        log.info("현재 로그인하신분의 권한 : " + memberuser.getRole().name());
        String role  = "";
        if("ADMIN".equals(memberuser.getRole().name())){   //auth 컬럼을 추가로 지정해서 사용
            log.info("관리자");
            role = Role.ADMIN.name();
        }else if("MASTER".equals(memberuser.getRole().name())){   //auth 컬럼을 추가로 지정해서 사용
            log.info("맛스타");
            role = Role.MASTER.name();
        }else {
            log.info("일반유저");
            role = Role.USER.name();
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username( memberuser.getEmail() )
                .password( memberuser.getPassword())
                .roles(role)
                .build();
    }


    public MemberuserDTO readMemberuser(String email){
        log.info( "read(String email)" + email);
        Memberuser memberuser = memberuserRepository.findCustomerByEmail(email);

        if (memberuser == null){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }
        MemberuserDTO memberuserDTO = modelMapper.map(memberuser, MemberuserDTO.class);
        return memberuserDTO;
    }


    //정보수정
    public void updateMemberuser(String email, MemberuserDTO memberuserDTO, PasswordEncoder passwordEncoder) {    //페스워드엔코딩 받아주기
        Memberuser memberuser = memberuserRepository.findByEmail(email);   //데이터베이스에서 주어진 이메일과 일치하는 사용자 정보를 가져와서 customer 변수에 넣음
        if (memberuser == null) {
            throw new IllegalStateException("사용자를 찾을 수 없습니다. " + email);
        }  if (memberuser != null) {
            memberuser.setPassword(passwordEncoder.encode(memberuserDTO.getPassword()));
        }
        memberuser.setName(memberuserDTO.getName());
        memberuser.setPhone(memberuserDTO.getPhone());
        memberuser.setNickname(memberuserDTO.getNickname());
        log.info(memberuser);
    }
    public void updatepw(String email,  String newPassword, PasswordEncoder passwordEncoder) {
        Memberuser memberuser = memberuserRepository.findByEmail(email);
        if (memberuser == null) {
            throw new IllegalStateException("사용자를 찾을 수 없습니다. " + email);
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        memberuser.setPassword(encodedPassword);

        // 변경된 사용자 정보를 데이터베이스에 저장
        memberuserRepository.save(memberuser);

        log.info("비밀번호가 변경된 사용자 정보: {}", memberuser);
    }


    //유저 리스트
    public PageResponseDTO<MemberuserDTO> list(PageRequestDTO pageRequestDTO) {
        Page<Memberuser> customerPage = memberuserRepository.findAll(pageRequestDTO.getPageable());
        //customerPage에 customerReposigtory를 사용해서 pageRequestDTO 안의 Pageable에서 모든걸 찾아온걸 담아줌(customer배열타입)
        List<MemberuserDTO> customerdDTOList =
                customerPage.getContent().stream()
                        .map(customer -> modelMapper.map(customer, MemberuserDTO.class))
                        .collect(Collectors.toList());

        log.info(customerdDTOList); //boardDTOList에는 bno등은 있지만 customerId는 없다. customer.customerId라서


        return PageResponseDTO.<MemberuserDTO>withAll()
                .dtoList(customerdDTOList)
                .pageRequestDTO(pageRequestDTO)
                .total((int) customerPage.getTotalElements())
                .build();
    }
    public Memberuser findEmail(String name){
        return memberuserRepository.findByName(name);
    }
    public Memberuser findPw(String  name, String email){
        return memberuserRepository.findByNameAndEmail(name,email);
    }

    public Memberuser checkCustomer(String name, String email){
        Memberuser memberuser =
                memberuserRepository.findByNameAndEmail(name, email);

        if (memberuser == null){
            return null;
        }



        return memberuser;

    }

    public void changePw (PasswordDTO passwordDTO){

        log.info("service에서 받은 DTO 확인 : " + passwordDTO);
        Memberuser memberuser =  memberuserRepository.findByEmail(passwordDTO.getEmail());
        log.info("검색한 customer값 : " + memberuser);
        if(memberuser !=null && memberuser.getName().equals(passwordDTO.getName())){
            memberuser.setPassword(passwordEncoder.encode(passwordDTO.getPassword2()));
        }

        log.info("customer 확인 : " + memberuser);

    }

    public String remove(int cno) {

        Memberuser memberuser = memberuserRepository
                .findById(cno)
                .orElseThrow(EntityNotFoundException::new);
        String nickname = memberuser.getNickname();
        memberuserRepository.deleteById(cno);

        return nickname;

    }



}
