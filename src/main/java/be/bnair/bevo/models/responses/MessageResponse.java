package be.bnair.bevo.models.responses;

import lombok.RequiredArgsConstructor;

/**
 * Cette classe représente une réponse de message contenant un code et un message.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@RequiredArgsConstructor
public class MessageResponse {
    /**
     * Le code associé à la réponse.
     */
    public final int code;

    /**
     * Le message de la réponse.
     */
    public final String message;
}
