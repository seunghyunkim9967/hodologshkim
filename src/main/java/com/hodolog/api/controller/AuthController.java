package com.hodolog.api.controller;

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

import static sun.security.x509.CertificateX509Key.KEY;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    /* byte -> String Base64 encode */
    /* String -> byte Base64 decode */

    /* 암호화 복호화 분리 */
    private static final String KEY = "rTPmA9Sgk+Q1XwuJbG7E6xFFUhQpdi+al5iyPnRTK/Q=";
    private final AuthService authService;

    //JWT 토큰 발급 

    //cookie
    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
        Long userId = authService.signin(login);

        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(KEY));

        String jws = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .signWith(key)
                .compact();

        return new SessionResponse(jws);
    }
}
