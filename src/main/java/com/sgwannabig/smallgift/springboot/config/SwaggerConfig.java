package com.sgwannabig.smallgift.springboot.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private String version;
    private String title;

    @Bean
    public Docket apiV1() {
        version = "V1";
        title = "REST API ";
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sgwannabig.smallgift.springboot.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(apiInfo(title, version))
                .securityContexts(Lists.newArrayList(securityContext())) // 61번째 주석 참고
                .securitySchemes(Lists.newArrayList(apiKey())); // 61번째 주석 참고
    }
    @Bean
    public Docket apiV2() {
        version = "V2";
        title = "REST API ";
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sgwannabig.smallgift.springboot.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(apiInfo(title, version));
    }

    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfo(
                title,
                "통합 API Docs",
                version,
                "www.example.com",
                new Contact("개발자에게 문의하기", "", ""),
                "Licenses",
                "www.example.com",
                new ArrayList<>());
    }
    // 이 밑에 부분들은 api 토큰이나 jwt같은 것들을 api에서 미리 인증할 수 있도록 도와줍니다.
    private ApiKey apiKey() {
        return new ApiKey("JWT","JWT" , "header");
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
    }
}
