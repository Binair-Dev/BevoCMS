package be.bnair.bevo.config.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;


import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

/**
 * Configuration pour la gestion des jetons JWT (JSON Web Tokens).
 * Cette classe contient les propriétés de configuration liées aux jetons JWT.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    /**
     * Durée de validité d'un jeton JWT en secondes.
     * Par défaut, un jeton expirera après 24 heures (86400 secondes).
     */
    public int expireAt = 86400;

    /**
     * Clé secrète utilisée pour signer et vérifier les jetons JWT.
     * Cette clé est générée automatiquement à l'aide de l'algorithme HS512.
     */
    public SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
}
