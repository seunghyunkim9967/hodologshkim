package com.hodolog.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "hodolman")
public class AppConfig {
    //application.yml의
    // hodolman:
    //   hello : "world" 가 들어가게 됨.
    private byte[] jwtKey;

    public void setJwtKey(String jwtKey) {
        this.jwtKey = Base64.getDecoder().decode(jwtKey);
    }

    public byte[] getJwtKey() {
        return jwtKey;
    }
}
