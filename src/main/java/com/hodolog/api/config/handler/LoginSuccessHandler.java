package com.hodolog.api.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodolog.api.config.UserPrincipal;
import com.hodolog.api.response.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        log.info("[로그인 인증 성공] user={}", principal.getUsername());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("403")
                .massage("접근할 수 없습니다.")
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8.name());
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
