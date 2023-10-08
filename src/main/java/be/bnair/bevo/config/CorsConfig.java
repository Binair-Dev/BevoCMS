package be.bnair.bevo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuration pour activer la prise en charge de CORS (Cross-Origin Resource Sharing).
 * Cette configuration permet à l'application d'accepter les requêtes provenant de domaines différents.
 *
 * @author Brian Van Bellinghen
 */
@Configuration
public class CorsConfig {

    /**
     * Configuration du filtre CORS pour autoriser les requêtes provenant de n'importe quelle origine ("*").
     *
     * Cette méthode crée un filtre CORS configuré pour accepter toutes les origines, méthodes et en-têtes,
     * permettant ainsi à l'application d'accepter les requêtes Cross-Origin Resource Sharing (CORS).
     *
     * @return Un filtre CORS configuré pour accepter toutes les origines, méthodes et en-têtes.
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
