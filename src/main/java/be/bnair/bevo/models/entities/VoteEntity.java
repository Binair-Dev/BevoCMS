package be.bnair.bevo.models.entities;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.security.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Cette classe représente une entité pour stocker des votes dans le système.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@Entity
@Table(name = "bevo_votes")
@Data
public class VoteEntity {
    /**
     * L'identifiant unique du vote.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * La date du vote.
     */
    private LocalDate date;

    /**
     * L'utilisateur qui a émis le vote.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}

