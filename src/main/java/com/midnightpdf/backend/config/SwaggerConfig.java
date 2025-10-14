package com.midnightpdf.backend.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("MidnightPDF Backend API")
                .version("1.0.0")
                .description("API para procesar archivos PDF y aplicar modo oscuro."));
    }
}
