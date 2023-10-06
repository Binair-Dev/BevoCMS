package be.bnair.bevo.models.responses;

import lombok.RequiredArgsConstructor;


/**
 * Cette classe représente une réponse d'authentification contenant un token.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@RequiredArgsConstructor
public class AuthResponse {
    /**
     * Le token d'authentification.
     */
    public final String token;
}
