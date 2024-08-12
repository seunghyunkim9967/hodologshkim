package com.hodolog.api.service;

import com.hodolog.api.Repository.UserRepository;
import com.hodolog.api.crypto.ScryptPasswordEncoder;
import com.hodolog.api.domain.Users;
import com.hodolog.api.exception.AlreadyExistsEmailException;
import com.hodolog.api.exception.InvalidSigninInformation;
import com.hodolog.api.request.Login;
import com.hodolog.api.request.Signup;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
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
        assertNotNull(user.getPassword());
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
        Long userId = authService.signin(login);

        //then
        Assertions.assertNotNull(userId);

    }

    @Test
    @DisplayName("로그인시 비밀번호 틀림")
    void test4() {
        //given
        ScryptPasswordEncoder encoder = new ScryptPasswordEncoder();

        String encryptedPassword = encoder.encrypt("1234");
        Users user = Users.builder()
                .email("dnfheh@naver.com")
                .password("1234")
                .name("짱돌군")
                .build();
        userRepository.save(user);

        Login login = Login.builder()
                .email("dnfheh@naver.com")
                .password("5678")
                .build();

        // expected
        Assertions.assertThrows(InvalidSigninInformation.class,
                () -> authService.signin(login));

    }

}