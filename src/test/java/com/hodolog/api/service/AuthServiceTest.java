package com.hodolog.api.service;

import com.hodolog.api.Repository.PostRepository;
import com.hodolog.api.Repository.UserRepository;
import com.hodolog.api.domain.Users;
import com.hodolog.api.request.Signup;
import org.apache.catalina.User;
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
                .password("1234")
                .email("dnfheh@naver.com")
                .name("승현맨")
                .build();
        //when
        authService.signup(signup);
        //then
        Assertions.assertEquals(1, userRepository.count());

        Users user = userRepository.findAll().iterator().next();
        assertEquals("1234", user.getPassword());
        assertEquals("dnfheh@naver.com", user.getEmail());
        assertEquals("승현맨", user.getName());


    }

    @Test
    @DisplayName("회원가입시 중복된 이메일")
    void test2() {
        //given
        Users user = Users.builder()
                .email("dnfheh@naver.com")
                .password("1234")
                .name("짱돌군")
                .build();
        userRepository.save(user);

        Signup signup = Signup.builder()
                .password("1234")
                .email("dnfheh@naver.com")
                .name("승현맨")
                .build();
        //when
        authService.signup(signup);
        //then
        Assertions.assertEquals(1, userRepository.count());
    }

}