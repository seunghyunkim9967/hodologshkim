package com.hodolog.api.config;

import com.hodolog.api.Repository.SessionRepository;
import com.hodolog.api.config.data.UserSession;
import com.hodolog.api.domain.Session;
import com.hodolog.api.exception.Unauthorized;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if (servletRequest == null) {
            log.error("servletRequest null");
            throw new Unauthorized();
        }
        Cookie[] cookies = servletRequest.getCookies();

        if (cookies.length == 0) {
            log.error("cookies 없음.");
            throw new Unauthorized();
        }

        String accessToken = cookies[0].getValue();
        System.out.println(accessToken);

        Session session = sessionRepository.findByAccessToken(accessToken) // null check SessionRepository
                .orElseThrow(Unauthorized::new);

        // 데이터베이스 사용자 확인작업

//        UserSession userSession = new UserSession(1L);
//        userSession.id = accessToken;
//        return userSession; 
        return new UserSession(session.getId());
    }
}
