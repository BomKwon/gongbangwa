package com.example.gongbangwa.repository;

import com.example.gongbangwa.entity.Memberuser;
import com.example.gongbangwa.repository.search.MemberuserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberuserRepository extends JpaRepository<Memberuser, Integer> , MemberuserRepositoryCustom {

    //이메일로 정보 찾기 //중복회원?? 혹은 로그인시?
    Memberuser findByEmail (String email);
    void deleteUserByEmail(String Email);
    Memberuser findByName(String name);
    Memberuser findByNameAndEmail(String name, String email);

    Memberuser findMemberuserByEmail(String email);

}
