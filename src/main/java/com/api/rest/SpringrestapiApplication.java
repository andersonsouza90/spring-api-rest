package com.api.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = {"com.api.rest.model"})
@ComponentScan(basePackages = {"com.*"})
@EnableJpaRepositories(basePackages = {"com.api.rest.repository"})
@EnableTransactionManagement
@EnableWebMvc
@RestController
public class SpringrestapiApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(SpringrestapiApplication.class, args);
	}
	
	//Mapeamento cross origin global que reflete em todo sistema
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/usuario/**")
		//.allowedMethods("POST")
		//.allowedOrigins("http://localhost:8080/springrestapi", "https://springboot-restapi-projeto.herokuapp.com/springrestapi")
		.allowedMethods("*")
		.allowedOrigins("*");
	}
	
	
}
