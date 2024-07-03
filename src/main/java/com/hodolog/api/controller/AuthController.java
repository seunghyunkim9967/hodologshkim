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

import java.security.Key;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    //JWT 토큰 발급 

    //cookie
    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
        String accessToken = authService.signin(login);

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        key.getEncoded();



        String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();

        return new SessionResponse(jws);
//        ResponseCookie cookie = ResponseCookie.from("SESSION", accessToken)
//                .domain("localhost") // todo 서버 환경에 따른 분리 필요 dev.myservice.com resource 폴더(로컬, 개발, 운영 구분하여 세팅)
//                .path("/")
//                .httpOnly(true)
//                .secure(false)
//                .maxAge(Duration.ofDays(30))
//                .sameSite("Strict")
//                .build();
//        log.info(">>>>>>>>> cookie={}", cookie.toString());
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.SET_COOKIE, cookie.toString())
//                .build();
    }
}
