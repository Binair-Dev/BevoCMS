package be.bnair.bevo.utils;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class AuthUtils {
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
