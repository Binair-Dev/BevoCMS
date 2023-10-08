package be.bnair.bevo.utils;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

/**
 * Classe utilitaire pour l'authentification dans l'application Bevo.
 * Cette classe fournit des méthodes pour extraire les détails de l'utilisateur à partir d'un jeton JWT.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
public class AuthUtils {

    /**
     * Récupère les détails de l'utilisateur à partir du jeton JWT dans l'en-tête de la requête HTTP.
     *
     * @param request   La requête HTTP.
     * @param jwtUtil   Utilitaire JWT pour extraire des informations du jeton.
     * @param userService   Service utilisateur pour rechercher l'utilisateur par nom d'utilisateur.
     * @return L'entité utilisateur correspondant au jeton JWT, ou null si l'utilisateur n'est pas authentifié.
     */
    public static UserEntity getUserDetailsFromToken(HttpServletRequest request, JwtUtil jwtUtil, UserService userService) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            Optional<UserEntity> optional = userService.getOneByUsername(jwtUtil.getUsernameFromToken(jwtToken));
            if(optional.isPresent()) return optional.get();
        }
        return null;
    }
}
