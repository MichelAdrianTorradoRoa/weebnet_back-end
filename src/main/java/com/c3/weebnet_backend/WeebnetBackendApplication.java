package com.c3.weebnet_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.c3.weebnet_backend.config.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
@ComponentScan(basePackages = "com.c3.weebnet_backend")
public class WeebnetBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeebnetBackendApplication.class, args);
	}

}
