package be.bnair.bevo.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Configuration personnalisée pour la documentation OpenAPI.
 * Cette configuration définit les détails de sécurité pour l'API.
 *
 * @author Brian Van Bellinghen
 */
@Configuration
public class OpenAPIConfig {

    /**
     * Configuration personnalisée de la documentation OpenAPI.
     *
     * Cette méthode crée une configuration OpenAPI personnalisée pour spécifier les détails de sécurité.
     *
     * @return Une instance de OpenAPI configurée.
     */
    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
