package be.bnair.bevo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        final String adminRank = "ADMINISTRATEUR";
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((registry) -> {
                    registry
                        //Enregistrement et Connexion
                        .requestMatchers("/auth/**").permitAll()

                        //Sécurités concernant les news
                        .requestMatchers("/news/list").permitAll()
                        .requestMatchers("/news/{id}").permitAll()
                        .requestMatchers("/news/delete/{id}").hasRole(adminRank)
                        .requestMatchers("/news/update/{id}").hasRole(adminRank)
                        .requestMatchers("/news/create").hasRole(adminRank)

                         //Sécurités concernant les offres paypal
                        .requestMatchers("/paypal-offer/list").permitAll()
                        .requestMatchers("/paypal-offer/{id}").permitAll()
                        .requestMatchers("/paypal-offer/delete/{id}").hasRole(adminRank)
                        .requestMatchers("/paypal-offer/update/{id}").hasRole(adminRank)
                        .requestMatchers("/paypal-offer/create").hasRole(adminRank)

                        //Sécurités concernant l'access au panel d'administration
                        .requestMatchers("/admin/**").hasRole(adminRank)
                        .anyRequest().authenticated();
                })
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
