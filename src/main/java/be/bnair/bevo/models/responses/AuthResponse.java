package be.bnair.bevo.models.responses;

import lombok.RequiredArgsConstructor;


/**
 * Cette classe représente une réponse d'authentification contenant un token.
 *
 * @author Brian Van Bellinghen
 */
@RequiredArgsConstructor
public class AuthResponse {
    /**
     * Le token d'authentification.
     */
    public final String token;
}
