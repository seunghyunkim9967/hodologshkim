package com.hodolog.api.config;

import com.hodolog.api.config.data.UserSession;
import com.hodolog.api.exception.Unauthorized;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = webRequest.getHeader("Authrization");

        if (accessToken == null || accessToken.equals("")) {
            throw new Unauthorized();
        }

        // 데이터베이스 사용자 확인작업

//        UserSession userSession = new UserSession(1L);
//        userSession.id = accessToken;
//        return userSession; 회식 그만 하고싶다
        return new UserSession(1L);
    }
}
