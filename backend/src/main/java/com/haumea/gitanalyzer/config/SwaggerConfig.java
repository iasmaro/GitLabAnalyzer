package com.haumea.gitanalyzer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket swaggerConfiguration() {
        //Return a prepared Docket instance
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.haumea"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "GitLabAnalyzer API",
                "Documentation for GitLabAnalyzer API.",
                "1.0",
                "Terms of service",
                new springfox.documentation.service.Contact("Haumea", "www.sfu.ca", "Haumea@sfu.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

}