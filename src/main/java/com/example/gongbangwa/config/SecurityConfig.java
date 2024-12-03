package com.example.gongbangwa.config;


import jakarta.servlet.annotation.WebListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@RequiredArgsConstructor
@WebListener
public class SecurityConfig {


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{

        http
                .authorizeHttpRequests(
                        (authorizeHttpRequests) -> authorizeHttpRequests
                                .requestMatchers(("/admin/**")).hasRole("ADMIN")
                                .requestMatchers("/favorite/**").authenticated()
                                .requestMatchers("/reserve/**").authenticated()
                                .requestMatchers("/css/**", "/js/**", "/img/**", "/**").permitAll()
                )

                .csrf((csrf) -> csrf

                        .disable()
                )
                //                .csrf( (csrf) -> csrf
//                .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))

                //현재 모든 경로에 대해서 csrf 미사용

                /*일반회원, 관리자 전용*/
                .formLogin(formLogin -> formLogin.loginPage("/login")
                        .defaultSuccessUrl("/session")
                        .usernameParameter("email")
                        .failureUrl("/login/error")

                )
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true));



        return http.build();

    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //  스프링 시큐리티의 인증 처리 , 인증은 로그인
    // AuthenticationManager는 사용자 인증시 앞에서 작성한 UserSecurityService 와 passwordEncoder를
    //내부적으로 사용 인증과 권한 부여




}
