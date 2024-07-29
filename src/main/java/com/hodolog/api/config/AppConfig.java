package com.hodolog.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "hodolman")
public class AppConfig {
    //application.yml의
    // hodolman:
    //   hello : "world" 가 들어가게 됨.
    public Map<String, String> hello;
}
