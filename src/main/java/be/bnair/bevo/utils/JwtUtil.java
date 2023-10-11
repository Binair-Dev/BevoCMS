package be.bnair.bevo.utils;

import be.bnair.bevo.models.entities.security.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import be.bnair.bevo.config.jwt.JwtConfig;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Utilitaire pour la gestion des tokens JWT (JSON Web Token).
 * Cette classe permet de générer, valider et extraire des informations à partir de tokens JWT.
 *
 * @author Brian Van Bellinghen
 */
@Component
public class JwtUtil {
    private final JwtConfig config;
    private JwtParser parser;
    private JwtBuilder builder;

    /**
     * Constructeur de la classe JwtUtil.
     *
     * @param config La configuration JWT de l'application.
     */
    public JwtUtil(JwtConfig config) {
        this.config = config;
        SecretKey key = this.config.secretKey;
        parser = Jwts.parser().setSigningKey(key);
        builder = Jwts.builder().signWith(key);
    }

    /**
     * Extrait le nom d'utilisateur à partir d'un token JWT.
     *
     * @param token Le token JWT à analyser.
     * @return Le nom d'utilisateur extrait du token.
     */
    public String getUsernameFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Extrait la date d'expiration à partir d'un token JWT.
     *
     * @param token Le token JWT à analyser.
     * @return La date d'expiration extraite du token.
     */
    public Date getExpirationDateFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Extrait la liste des rôles (autorités) à partir d'un token JWT.
     *
     * @param token Le token JWT à analyser.
     * @return La liste des rôles extraite du token.
     */
    public List<String> getAuthoritiesFromToken(String token) {
        return this.getClaimFromToken(token, (claims) -> claims.get("roles", List.class));
    }

    /**
     * Vérifie si un token JWT a expiré.
     *
     * @param token Le token JWT à vérifier.
     * @return True si le token a expiré, False sinon.
     */
    public boolean isExpire(String token) {
        Date eDate = this.getClaimFromToken(token, Claims::getExpiration);

        return eDate.before(new Date());
    }

    /**
     * Extrait une revendication (claim) spécifique à partir d'un token JWT.
     *
     * @param token         Le token JWT à analyser.
     * @param claimResolver La fonction pour extraire la revendication souhaitée.
     * @param <T>           Le type de la revendication.
     * @return La revendication extraite du token.
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getClaimsFromToken(String token) {

        return parser
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Valide un token JWT par rapport aux informations de l'utilisateur.
     *
     * @param token       Le token JWT à valider.
     * @param userDetails Les détails de l'utilisateur à comparer.
     * @return True si le token est valide, False sinon.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        boolean hasSameSubject = getUsernameFromToken(token).equals(userDetails.getUsername());
        return hasSameSubject && !isExpire(token);
    }

    /**
     * Génère un token JWT à partir des informations de l'utilisateur.
     *
     * @param userDetails Les détails de l'utilisateur pour générer le token.
     * @return Le token JWT généré.
     */
    public String generateToken(UserDetails userDetails) {
        UserEntity entity = (UserEntity) userDetails;
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().toArray());
        claims.put("id", entity.getId());
        return generateToken(claims, userDetails.getUsername());
    }

    private String generateToken(Map<String, Object> claims, String subject) {
        return builder
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + config.expireAt* 1000L))
                .compact();
    }
}
