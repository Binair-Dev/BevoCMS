package be.bnair.bevo.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import be.bnair.bevo.utils.JwtUtil;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import be.bnair.bevo.utils.JwtUtil;

import java.io.IOException;

/**
 * Filtre pour gérer l'authentification par jeton JWT (JSON Web Token).
 * Ce filtre intercepte les requêtes entrantes, vérifie les jetons JWT et authentifie les utilisateurs.
 *
 * Le filtre extrait l'en-tête "Authorization" de chaque requête HTTP, puis valide et authentifie les utilisateurs
 * en fonction du jeton JWT fourni dans l'en-tête.
 *
 * Pour fonctionner correctement, ce filtre dépend de deux composants :
 * - Un service d'interface {@link UserDetailsService} pour charger les détails de l'utilisateur en fonction du nom d'utilisateur.
 * - Un utilitaire {@link JwtUtil} pour extraire et valider les jetons JWT.
 *
 * @author Brian Van Bellinghen
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtUtil utils;

    /**
     * Constructeur de la classe JwtFilter.
     *
     * @param userDetailsService Le service qui charge les détails de l'utilisateur.
     * @param utils              L'utilitaire pour manipuler les jetons JWT.
     */
    public JwtFilter(UserDetailsService userDetailsService, JwtUtil utils) {
        this.userDetailsService = userDetailsService;
        this.utils = utils;
    }

    /**
     * Méthode principale qui gère la logique de filtrage des requêtes entrantes.
     *
     * @param request       La requête HTTP entrante.
     * @param response      La réponse HTTP.
     * @param filterChain   La chaîne de filtres.
     * @throws ServletException En cas d'erreur lors du traitement de la requête.
     * @throws IOException      En cas d'erreur d'entrée/sortie lors du traitement de la requête.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            String[] authorizations = authorization.split(" ");
            String type = authorizations[0];
            String token = authorizations[1];

            if (type.equals("Bearer") && !token.equals("")) {
                String username = this.utils.getUsernameFromToken(token);
                UserDetails user = this.userDetailsService.loadUserByUsername(username);
                if (this.utils.validateToken(token, user)) {
                    UsernamePasswordAuthenticationToken upt = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(upt);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}

