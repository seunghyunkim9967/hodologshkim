package com.hodolog.api.config;


import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = HodologMockSecurityContext.class)
public @interface HodologMockUser {

    String name() default "승현맨";

    String email() default "dnfheh@naver.com";

    String password() default "";

//    String role() default "ROLE_ADMIN";
}
