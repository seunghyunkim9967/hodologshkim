package com.hodolog.api.service;

import com.hodolog.api.Repository.PostRepository;
import com.hodolog.api.Repository.UserRepository;
import com.hodolog.api.domain.Users;
import com.hodolog.api.exception.AlreadyExistsEmailException;
import com.hodolog.api.request.Login;
import com.hodolog.api.request.Signup;
import org.apache.catalina.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.rmi.AlreadyBoundException;

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
        assertNotEquals("1234", user.getPassword());
        assertNotNull("1234", user.getPassword());
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
        //expected
        assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(signup));
    }

    @Test
    @DisplayName("로그인 성공")
    void test3() {
        //given
        Signup signup = Signup.builder()
                .password("1234")
                .email("dnfheh@naver.com")
                .name("승현맨")
                .build();
        authService.signup(signup);

        Login login = Login.builder()
                .email("dnfheh@naver.com")
                .password("1234")
                .build();

        // when
        // authService.signin 내부 encoder.matches 사용 유무로 테스트 결과가 달라짐 ( hash 값 복호화 )
        authService.signin(login);

        //then


    }

}