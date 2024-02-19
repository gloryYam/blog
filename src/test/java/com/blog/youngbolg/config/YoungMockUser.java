package com.blog.youngbolg.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = YoungMockSecurityContext.class)
public @interface YoungMockUser {

    String name() default "김영광";

    String nickName() default "글로리";

    String email() default "dudrhkd4179@naver.com";

    String password() default "";

}
