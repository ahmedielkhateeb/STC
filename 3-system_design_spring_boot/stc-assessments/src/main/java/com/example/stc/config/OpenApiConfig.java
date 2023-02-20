package com.example.stc.config;

import java.util.Collections;
import java.util.function.Predicate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class OpenApiConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization", "X-Requested-With")
                .exposedHeaders("header1", "header2")
                .allowCredentials(false).maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    @Bean
    public Docket configApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.stc.controllers"))
                .paths(PathSelectors.any())
                .build();
    }

    private Predicate<String> paths() {
        return ((Predicate<String>) PathSelectors.regex("/api/v1.*")::apply).or(PathSelectors.regex("/api/v1/*")::apply);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("STC Assessment")
                .description("STC Assessment API Information")
                .version("1.0")
                .termsOfServiceUrl("Free to use")
                .contact(new Contact("Ahmed Ibrahim", "https://www.linkedin.com/in/ahmedel-khateeb/", "ahmedelkhateebco@gmail.com"))
                .license("API License")
                .licenseUrl("https://www.linkedin.com/in/ahmedel-khateeb/")
                .extensions(Collections.emptyList())
                .build();
    }
}
