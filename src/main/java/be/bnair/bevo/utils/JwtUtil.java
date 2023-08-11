package be.bnair.bevo.utils;

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

@Component
public class JwtUtil {
    private final JwtConfig config;
    private JwtParser parser;
    private JwtBuilder builder;

    public JwtUtil(JwtConfig config) {
        this.config = config;
        SecretKey key = this.config.secretKey;
        parser = Jwts.parser().setSigningKey(key);
        builder = Jwts.builder().signWith(key);
    }

    public String getUsernameFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getExpiration);
    }

    public List<String> getAuthoritiesFromToken(String token) {
        return this.getClaimFromToken(token, (claims) -> claims.get("roles", List.class));
    }

    public boolean isExpire(String token) {
        Date eDate = this.getClaimFromToken(token, Claims::getExpiration);

        return eDate.before(new Date());
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getClaimsFromToken(String token) {

        return parser
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        boolean hasSameSubject = getUsernameFromToken(token).equals(userDetails.getUsername());

        return hasSameSubject && !isExpire(token);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().toArray());
        return generateToken(claims, userDetails.getUsername());
    }

    private String generateToken(Map<String, Object> claims, String subject) {
        SecretKey key = this.config.secretKey;
        return builder
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + config.expireAt* 1000L))
                .compact();
    }
}
