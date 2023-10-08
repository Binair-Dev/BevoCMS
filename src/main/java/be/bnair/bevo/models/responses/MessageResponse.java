package be.bnair.bevo.models.responses;

import lombok.RequiredArgsConstructor;

/**
 * Cette classe représente une réponse de message contenant un code et un message.
 *
 * @author Brian Van Bellinghen
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
