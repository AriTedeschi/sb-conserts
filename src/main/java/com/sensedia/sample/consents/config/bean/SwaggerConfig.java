package com.sensedia.sample.consents.config.bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI caseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Consent Service")
                        .description("Consent Service API Documentation")
                        .version("0.0.1-SNAPSHOT")
                );
    }
}