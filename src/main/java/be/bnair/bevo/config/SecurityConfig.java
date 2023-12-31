package be.bnair.bevo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import be.bnair.bevo.config.jwt.JwtFilter;

/**
 * Configuration de la sécurité de l'application.
 * Cette configuration définit les règles de sécurité pour l'accès aux ressources et services de l'application.
 *
 * @author Brian Van Bellinghen
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    /**
     * Configuration du codeur de mot de passe pour le stockage sécurisé des mots de passe.
     *
     * @return Un codeur de mot de passe BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuration de la chaîne de filtres de sécurité pour gérer les requêtes HTTP.
     *
     * Cette méthode déclare les règles de sécurité pour les différentes URL de l'application,
     * spécifiant les autorisations requises pour accéder aux ressources.
     *
     * @param http      L'objet HttpSecurity pour configurer les règles de sécurité.
     * @param jwtFilter Le filtre JWT pour l'authentification basée sur les jetons JWT.
     * @return Une chaîne de filtres de sécurité configurée.
     * @throws Exception En cas d'erreur de configuration de la sécurité.
     */
    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        final String adminRank = "ADMINISTRATEUR";
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((registry) -> {
                    registry
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()

                        //Enregistrement et Connexion
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/register").permitAll()
                        .requestMatchers("/infos/stats").permitAll()
                        .requestMatchers("/skins/upload").authenticated()
                        .requestMatchers("/skins/{fileName:.+}").permitAll()
                        .requestMatchers("/shop-transactions/history").authenticated()
                        .requestMatchers("/images/upload").hasRole(adminRank)
                        .requestMatchers("/images/delete/{fileName:.+}").hasRole(adminRank)
                        .requestMatchers("/images/get/**").permitAll()

                        //Sécurités concernant les news
                        .requestMatchers("/news/list").permitAll()
                        .requestMatchers("/news/list/{limit}").permitAll()
                        .requestMatchers("/news/{id}").permitAll()
                        .requestMatchers("/news/delete/{id}").hasRole(adminRank)
                        .requestMatchers("/news/update/{id}").hasRole(adminRank)
                        .requestMatchers("/news/create").hasRole(adminRank)

                         //Sécurités concernant les offres paypal
                        .requestMatchers("/paypal-offers/delete/{id}").hasRole(adminRank)
                        .requestMatchers("/paypal-offers/update/{id}").hasRole(adminRank)
                        .requestMatchers("/paypal-offers/create").hasRole(adminRank)

                        //Sécurités concernant les règles
                        .requestMatchers("/rules/delete/{id}").hasRole(adminRank)
                        .requestMatchers("/rules/update/{id}").hasRole(adminRank)
                        .requestMatchers("/rules/create").hasRole(adminRank)

                         //Sécurités concernant les serveurs
                        .requestMatchers("/servers/list").hasRole(adminRank)
                        .requestMatchers("/servers/{id}").hasRole(adminRank)
                        .requestMatchers("/servers/delete/{id}").hasRole(adminRank)
                        .requestMatchers("/servers/update/{id}").hasRole(adminRank)
                        .requestMatchers("/servers/create").hasRole(adminRank)

                        //Sécurités concernant les ranks
                        .requestMatchers("/ranks/list").hasRole(adminRank)
                        .requestMatchers("/ranks/{id}").hasRole(adminRank)
                        .requestMatchers("/ranks/delete/{id}").hasRole(adminRank)
                        .requestMatchers("/ranks/update/{id}").hasRole(adminRank)
                        .requestMatchers("/ranks/create").hasRole(adminRank)

                        //Sécurités concernant les catégories shop
                        .requestMatchers("/shop-categories/delete/{id}").hasRole(adminRank)
                        .requestMatchers("/shop-categories/update/{id}").hasRole(adminRank)
                        .requestMatchers("/shop-categories/create").hasRole(adminRank)

                        //Sécurités concernant les wiki
                        .requestMatchers("/wikis/delete/{id}").hasRole(adminRank)
                        .requestMatchers("/wikis/update/{id}").hasRole(adminRank)
                        .requestMatchers("/wikis/create").hasRole(adminRank)

                        //Sécurités concernant les ShopItem
                        .requestMatchers("/shop-items/delete/{id}").hasRole(adminRank)
                        .requestMatchers("/shop-items/update/{id}").hasRole(adminRank)
                        .requestMatchers("/shop-items/create").hasRole(adminRank)

                        //Sécurités concernant les ShopTransactions
                        .requestMatchers("/shop-transactions/create").hasRole(adminRank)
                        .requestMatchers("/shop-transactions/create").hasRole(adminRank)
                        .requestMatchers("/shop-transactions/create").hasRole(adminRank)
                        .requestMatchers("/shop-transactions/create").hasRole(adminRank)

                        //Sécurités concernant les Transactions
                        .requestMatchers("/transactions/create").hasRole(adminRank)
                        .requestMatchers("/transactions/delete/{id}").hasRole(adminRank)
                        .requestMatchers("/transactions/update/{id}").hasRole(adminRank)

                        .requestMatchers("/vote-rewards/delete/{id}").hasRole(adminRank)
                        .requestMatchers("/vote-rewards/update/{id}").hasRole(adminRank)
                        .requestMatchers("/vote-rewards/create").hasRole(adminRank)

                        //Sécurité concernant les Votes
                        .requestMatchers("/votes/delete/{id}").hasRole(adminRank)
                        .requestMatchers("/votes/update/{id}").hasRole(adminRank)
                        .requestMatchers("/votes/create").hasRole(adminRank)

                        //Sécurités concernant les utilisateurs
                        .requestMatchers("/users/list").hasRole(adminRank)
                        .requestMatchers("/users/{id}").permitAll()
                        .requestMatchers("/users/delete/{id}").hasRole(adminRank)
                        .requestMatchers("/users/update/{id}").authenticated()
                        .requestMatchers("/users/update/email").authenticated()
                        .requestMatchers("/users/update/password").authenticated()
                        .requestMatchers("/users/create").hasRole(adminRank)

                        .requestMatchers("/dedipass/check").permitAll()

                        //Sécurités concernant l'access au panel d'administration
                        .requestMatchers("/admin/**").hasRole(adminRank)
                        .anyRequest().authenticated();
                })
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
