package com.hodolog.api.crypto;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("default") /* 운영 : password 암호화 */
@Component
public class ScryptPasswordEncoder implements PasswordEncoder{
    //1. 암호화 O -> 운영 ScryptPassowrdEncoder
    //2. 평문 ( 암호화 X ) 로컬 테스트용 -> 평문 password를 AuthService에 주입
    private static final SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(
            16,
            8,
            1,
            32,
            64);

    @Override
    public String encrypt(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encrpytedPassword) {
        return encoder.matches(rawPassword, encrpytedPassword);
    }



}
