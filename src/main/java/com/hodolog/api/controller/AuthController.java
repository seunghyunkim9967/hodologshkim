package com.hodolog.api.controller;

import com.hodolog.api.Repository.UserRepository;
import com.hodolog.api.request.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/auth/login")
    public void login(@RequestBody Login login) {
        // json 아이디/비밀번호
        log.info(">>>login = {}", login);
        // DB에서 조회
        
        // 토큰을 응답

    }
}
