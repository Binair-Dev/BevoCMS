package be.bnair.bevo.models.responses;

import lombok.RequiredArgsConstructor;


/**
 * Cette classe représente une réponse d'authentification contenant un token.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@RequiredArgsConstructor
public class AuthResponse {
    /**
     * Le token d'authentification.
     */
    public final String token;
}
