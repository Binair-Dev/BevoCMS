package be.bnair.bevo.models.entities;

import be.bnair.bevo.models.entities.security.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Cette classe représente une entité pour stocker des transactions de la boutique dans le système.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@Entity
@Table(name = "bevo_shop_transactions")
@Data
public class ShopTransactionEntity {
    /**
     * L'identifiant unique de la transaction de la boutique.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * L'article associé à la transaction.
     */
    @ManyToOne
    private ShopItemEntity item;

    /**
     * Le montant de crédit associé à la transaction.
     */
    private double credit;

    /**
     * L'utilisateur effectuant la transaction.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
