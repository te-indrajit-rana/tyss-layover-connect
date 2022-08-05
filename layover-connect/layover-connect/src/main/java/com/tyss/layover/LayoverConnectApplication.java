package com.tyss.layover;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@EnableAsync
@SpringBootApplication
//@SecurityScheme(name = "layover-api", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@OpenAPIDefinition(info = @Info(title = "Layover Connect API", version = "1.0", description = "Layover Connect Application Development"))
public class LayoverConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LayoverConnectApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModules(new JavaTimeModule());
		return objectMapper;
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
