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

    @PostMapping("/auth/signup")
    public void signup(@RequestBody Signup signup) {
        authService.signup(signup);
    }
}
