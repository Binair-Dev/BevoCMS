package be.bnair.bevo.models;

import lombok.Data;

/**
 * Cette classe représente des informations statistiques.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@Data
public class Information {
    /**
     * Le nombre d'inscriptions.
     */
    private long register_count;

    /**
     * Le nombre de vues.
     */
    private int view_count;

    /**
     * Les joueurs connectés.
     */
    private String connected_players;

    /**
     * Le nombre de membres du personnel.
     */
    private int staff;

    public Information(long register_count, int view_count, String connected_players, int staff) {
        this.register_count = register_count;
        this.view_count = view_count;
        this.connected_players = connected_players;
        this.staff = staff;
    }
}
