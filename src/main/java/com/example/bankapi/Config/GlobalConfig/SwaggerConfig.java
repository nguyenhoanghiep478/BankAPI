package com.example.bankapi.Config.GlobalConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createApiDocument(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(getAppInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.bankapi.Controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getAppInfo() {
        return new ApiInfoBuilder()
                .title("Bank Application API")
                .description("API documentation for Bank Application")
                .version("1.0")
                .build();
    }
}
