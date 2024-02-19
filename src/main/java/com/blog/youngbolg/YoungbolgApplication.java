package com.blog.youngbolg;

import com.blog.youngbolg.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties(AppConfig.class)
@EnableJpaAuditing
@SpringBootApplication
public class YoungbolgApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoungbolgApplication.class, args);
	}

}
