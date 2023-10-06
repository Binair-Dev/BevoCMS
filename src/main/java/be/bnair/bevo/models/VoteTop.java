package be.bnair.bevo.models;

import lombok.Builder;
import lombok.Data;

/**
 * Cette classe représente les meilleurs votants.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@Data
@Builder
public class VoteTop {
    /**
     * Le nom de l'utilisateur.
     */
    private String user_name;

    /**
     * Le nombre de votes.
     */
    private int vote_amount;
}
