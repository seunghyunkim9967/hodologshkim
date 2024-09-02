package com.hodolog.api.config;

import com.hodolog.api.Repository.UserRepository;
import com.hodolog.api.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

@RequiredArgsConstructor
public class HodologMockSecurityContext implements WithSecurityContextFactory<HodologMockUser> {

    private final UserRepository userRepository;

    @Override
    public SecurityContext createSecurityContext(HodologMockUser annotation) {
        var user = Users.builder()
                .email(annotation.email())
                .name(annotation.name())
                .password(annotation.password())
                .build();

        userRepository.save(user);

        var principal = new UserPrincipal(user);

        var role = new SimpleGrantedAuthority("ROLE_ADMIN");

        var authenticationToken = new UsernamePasswordAuthenticationToken(principal,
                user.getPassword(),
                List.of(role)
                );

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);

        return context;
    }
}
