package com.hodolog;

import com.hodolog.api.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class) // AppConfig의 ConfigurationProperties 에 @Configration 어노테이션 보다 선호함.
@SpringBootApplication
public class HodologApplication {

    public static void main(String[] args) {
        SpringApplication.run(HodologApplication.class, args);
    }

    /*
    * 배포 관련
    * 프로세스 자동화
    *
    * 2. 서버 -
    * 네트워크를 통해 정보나 서비스를 제공하는 장치 or 프로그램
    * 24시간 운영
    * 웹,서버 호스팅
    * 클라우드 (AWS,Azure) : 이용한 만큼 지불, 설정과 관리 확장성 좋음(비싸다)
    * 웹호스팅 : 하나의 서버(OS에) 여러 사용자 입주, 가격저렴, 환경 제한(주로 PHP), 트래픽 제한, 속도 느림, 도메인 등록 제한
    *
    * * 3. 배포방식 : 개발서버 -> 베타서버 -> 스테이징서버 -> 운영서버
    *
    *
    *
    * */

}
