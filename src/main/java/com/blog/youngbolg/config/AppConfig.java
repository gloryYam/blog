package com.blog.youngbolg.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Base64;

@Data
@ConfigurationProperties("glory")
public class AppConfig {

    private byte[] jwtKey;

    public void setJwtKey(String jwtKey) {
        this.jwtKey = Base64.getDecoder().decode(jwtKey);
    }

}
