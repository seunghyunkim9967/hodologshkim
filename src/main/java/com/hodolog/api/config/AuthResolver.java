package com.hodolog.api.config;

import com.hodolog.api.Repository.SessionRepository;
import com.hodolog.api.config.data.UserSession;
import com.hodolog.api.domain.Session;
import com.hodolog.api.exception.Unauthorized;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;

import static sun.security.x509.CertificateX509Key.KEY;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

//    private static final String KEY = "rTPmA9Sgk+Q1XwuJbG7E6xFFUhQpdi+alSiyPnRTK/Q=";
    private final SessionRepository sessionRepository;
    private final AppConfig appConfig;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //sout System.out.println();
        log.info(">>>{}", appConfig.toString());
        // List : appConfig = {AppConfig@10488} "AppConfig(hello=[a, b, c, d])" -> c
        // Map  :
//        System.out.println(appConfig.hello.get("name"));

        String jws = webRequest.getHeader("Authorization");
        if (jws == null || jws.equals("")) {
            throw new Unauthorized();
        }

        byte[] decodedKey = Base64.decodeBase64(appConfig.jwtKey);

        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(decodedKey)
                    .build()
                    .parseClaimsJws(String.valueOf(jws));
            String userId = claims.getBody().getSubject();
//            log.info(">>>>>", claims);
            //claims.getBody().getSubject() -> result = Joe (AuthController auth/login)
            //OK, we can trust this JWT
            return new UserSession(Long.parseLong(userId));
        } catch (JwtException e) {
            throw new Unauthorized();
            //don't trust the JWT!
        }
                                                       
        //JWT를 이용한 인증 -> DB조회 필요 없음.
//        return null;
        //new UserSession(session.getId());
    }
}
