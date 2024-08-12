package com.hodolog.api.crypto;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test") /* 테스트 : 테스트, 로컬 password 평문 */
@Component
public class PlainPasswordEncoder implements PasswordEncoder{

    @Override
    public String encrypt(String rawPassword) {
        return rawPassword;
    }

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return rawPassword.equals(encryptedPassword);
    }
}
