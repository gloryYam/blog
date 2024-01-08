package com.blog.youngbolg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //    /**
    //     * 서버에서 CORS 해결
    //     */
    //    @Override
    //    public void addCorsMappings(CorsRegistry registry) {
    //        registry.addMapping("/**")
    //                .allowedOrigins("http://127.0.0.1:3000/");
    //    }
}
