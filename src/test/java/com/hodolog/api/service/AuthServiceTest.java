package com.hodolog.api.service;

import com.hodolog.api.Repository.PostRepository;
import com.hodolog.api.Repository.UserRepository;
import com.hodolog.api.request.Signup;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1() {
        //given
        //인스턴스 넘김
        Signup signup = Signup.builder()
                .account("seunghyunman")
                .password("1234")
                .email("dnfheh@naver.com")
                .build();
        //when
        authService.signup(signup);
        //then
        Assertions.assertEquals(2, userRepository.count());
    }

}