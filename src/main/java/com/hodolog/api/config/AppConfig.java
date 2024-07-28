package com.hodolog.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hodolman")
public class AppConfig {
    //application.yml의
    // hodolman:
    //   hello : "world" 가 들어가게 됨.
    public String hello;
}
