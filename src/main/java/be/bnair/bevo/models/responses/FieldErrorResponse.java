package be.bnair.bevo.models.responses;

import lombok.RequiredArgsConstructor;
import java.util.List;

/**
 * Cette classe représente une réponse d'erreur de champ contenant un code d'erreur et une liste de messages d'erreur.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@RequiredArgsConstructor
public class FieldErrorResponse {
    /**
     * Le code d'erreur.
     */
    public final int code;

    /**
     * La liste des messages d'erreur.
     */
    public final List<String> messages;
}
