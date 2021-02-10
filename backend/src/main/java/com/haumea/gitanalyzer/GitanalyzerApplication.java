package com.haumea.gitanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@SpringBootApplication
public class GitanalyzerApplication {

	public static void main(String[] args) {

		SpringApplication.run(GitanalyzerApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration(){
		//Return a prepared Docket instance
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.haumea"))
				.build();
	}

}
