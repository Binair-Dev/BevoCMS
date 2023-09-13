package be.bnair.bevo.utils;

import be.bnair.bevo.models.entities.security.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUtils {

    public static UserDetails getUserDetailsFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        throw new RuntimeException("L'utilisateur n'existe pas ou n'est pas authentifié.");
    }

    public static UserEntity getUserEntityFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            return (UserEntity) authentication.getPrincipal();
        }
        throw new RuntimeException("L'utilisateur n'existe pas ou n'est pas authentifié.");
    }
}
