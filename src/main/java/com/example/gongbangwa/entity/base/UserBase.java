package com.example.gongbangwa.entity.base;

import com.example.gongbangwa.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.PipedReader;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public class UserBase extends Base{

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


}
