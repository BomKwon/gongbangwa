package com.example.gongbangwa.entity;

import com.example.gongbangwa.constant.Role;
import com.example.gongbangwa.dto.MemberuserDTO;
import com.example.gongbangwa.entity.base.Base;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Table(name = "memberuser")
public class Memberuser extends Base{ // UserDetails를 상속받아 인증 객체로 사용

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;            //회원번호

    @Column(unique = true)
    private String email;       //이메일

    @Column(nullable = false, length = 100)
    private String password;    //비밀번호

    @Column
    private String name;       //이름

    @Column
    private String phone;       //전화번호

    @Enumerated(EnumType.STRING)
    private Role role;          //권한

    @Column
    private String nickname;    //별명



    //회원가입용
    public static Memberuser createMemeberuser(MemberuserDTO memberuserDTO,
                                            PasswordEncoder passwordEncoder) {
        //modelmapper
        Memberuser memberuser = new Memberuser();
        memberuser.setEmail(memberuserDTO.getEmail());
        String password =  passwordEncoder.encode(memberuserDTO.getPassword());
        memberuser.setPassword(password);
        memberuser.setName(memberuserDTO.getName());
        memberuser.setPhone(memberuserDTO.getPhone());
        memberuser.setNickname(memberuserDTO.getNickname());
        memberuser.setRole(memberuserDTO.getRole());      // 사용자가 가입했을때

        return memberuser;
    }

}
