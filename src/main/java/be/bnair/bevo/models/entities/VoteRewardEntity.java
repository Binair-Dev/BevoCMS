package be.bnair.bevo.models.entities;

import be.bnair.bevo.models.enums.EnumRewardType;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Cette classe représente une entité pour stocker des récompenses de vote dans le système.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@Entity
@Table(name = "bevo_vote_rewards")
@Data
public class VoteRewardEntity {
    /**
     * L'identifiant unique de la récompense de vote.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Le titre de la récompense de vote.
     */
    private String title;

    /**
     * Le pourcentage de la récompense de vote.
     */
    private double percent;

    /**
     * Le type de récompense de vote (EnumRewardType).
     */
    @Enumerated(EnumType.STRING)
    private EnumRewardType rewardType;

    /**
     * La commande associée à la récompense de vote.
     */
    private String command;

    /**
     * Le crédit associé à la récompense de vote.
     */
    private double credit;

    /**
     * Le serveur associé à la récompense de vote.
     */
    @ManyToOne
    @JoinColumn(name = "server_id")
    private ServerEntity server;
}

