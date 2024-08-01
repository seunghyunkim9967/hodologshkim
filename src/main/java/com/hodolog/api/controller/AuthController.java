package com.hodolog.api.controller;

import com.hodolog.api.config.AppConfig;
import com.hodolog.api.request.Login;
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

import static sun.security.x509.CertificateX509Key.KEY;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    /* byte -> String Base64 encode */
    /* String -> byte Base64 decode */

    /* 암호화 복호화 분리 */
    private final AuthService authService;
    private final AppConfig appConfig;

    //JWT 토큰 발급 

    //cookie
    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
        Long userId = authService.signin(login);

//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        Base64.getEncoder().encode(key.getEncoded());
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(appConfig.getJwtKey()));
        //Evaluate expression 확인
        //appConfig.jwtKey result :  jwtKey = "rTPmA9Sgk+Q1XwuJbG7E6xFFUhQpdi+al5iyPnRTK/Q="
        /* compact부분 에러
        *  UnavailableImplementationException : jjwt-jackson -> 토큰을 생성할때 자동으로 Jackson 라이브러리를 가져다 Json 매핑
        *  compact() 함수 호출할때 Serializer = 커스텀으로 구현하거나 구현체를 넣어줘야함
        *  하지만 난 해결안됨 왜 와이
        * */

        String jws = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();

        return new SessionResponse(jws);
    }
}
