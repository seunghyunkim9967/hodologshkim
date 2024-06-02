package com.hodolog.api.controller;

import com.hodolog.api.Repository.UserRepository;
import com.hodolog.api.domain.User;
import com.hodolog.api.exception.InvalidRequest;
import com.hodolog.api.exception.InvalidSigninInformation;
import com.hodolog.api.request.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/auth/login")
    public User login(@RequestBody Login login) {
        // json 아이디/비밀번호
        log.info(">>>login = {}", login);
        // DB에서 조회
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);
        // 토큰을 응답
        return user;
    }
}
