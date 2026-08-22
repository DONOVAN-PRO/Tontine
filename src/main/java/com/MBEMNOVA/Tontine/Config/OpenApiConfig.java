package com.MBEMNOVA.Tontine.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI tontineOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gestion Tontine")
                        .description("Gestion des tontines, membres, cotisations et tours de paiement.")
                        .version("1.0.0"));
    }
}