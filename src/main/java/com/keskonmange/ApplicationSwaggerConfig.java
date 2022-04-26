package com.keskonmange;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@Configuration
@EnableSwagger2
public class ApplicationSwaggerConfig {

	@Bean
	public Docket keskonmangeApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.keskonmange")) 
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiDetails());
	}
	
	//modification des info de l'API
	private ApiInfo apiDetails() {
		return new ApiInfo(
				"KesKonMange API",
				"Sample API for KesKonMange app",
				"1.0",
				"Free to use",
				new springfox.documentation.service.Contact("KKM", null, null),
				"API License",
				"hhtp://localhost:8080",
				Collections.emptyList());
	}

}
