package com.hodolog.api.controller;

import com.hodolog.api.config.AppConfig;
import com.hodolog.api.request.Signup;
import com.hodolog.api.response.SessionResponse;
import com.hodolog.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.Security;
import java.util.Base64;
import java.util.Date;



@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    /* byte -> String Base64 encode */
    /* String -> byte Base64 decode */

    /* spring-security : localhost:root 접속 시 /login (자동 권한체크) 개발자 도구 -> Network -> Headers 확인 + Payload의 Form Data 확인 (어떤식으로 동작하는지 확인)
    *  Set-Cookie: JSESSIONID 생성 ( 사용자 인증 값인 쿠키생성 ) -> 로그인이 됐으니 서버는 root 페이지로 이동됨.
    *  Cookie 인증 값이 없으면 당연히 login 페이지로 이동됨
    *   */

    /* 암호화 복호화 분리 */
    private final AuthService authService;
    private final AppConfig appConfig;

    @PostMapping("/auth/signup")
    public void signup(@RequestBody Signup signup) {
        authService.signup(signup);
    }
}
