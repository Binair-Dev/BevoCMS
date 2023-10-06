package be.bnair.bevo.models.entities;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.security.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Cette classe représente une entité pour stocker des transactions dans le système.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@Entity
@Table(name = "bevo_transactions")
@Data
public class TransactionEntity {
    /**
     * L'identifiant unique de la transaction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Le montant de crédit associé à la transaction.
     */
    private double credit;

    /**
     * Le prix de la transaction.
     */
    private double price;

    /**
     * La date de la transaction.
     */
    private LocalDate date;

    /**
     * L'utilisateur lié à la transaction.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /**
     * L'identifiant de la transaction.
     */
    private String identifier;
}

