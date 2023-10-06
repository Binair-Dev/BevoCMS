package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.ShopTransactionEntity;
import be.bnair.bevo.models.entities.WikiEntity;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * Cette classe représente un formulaire de transaction de boutique.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@Data
public class ShopTransactionForm {
    /**
     * L'identifiant de l'article de boutique associé à la transaction.
     */
    @Min(value = 1, message = "L'identifiant de l'article ne peut pas être vide")
    private Long item;

    /**
     * Le montant de crédit de la transaction.
     */
    @Min(value = 1, message = "Les crédits ne peuvent pas être vide")
    private double credit;

    /**
     * L'identifiant de l'utilisateur effectuant la transaction.
     */
    @Min(value = 1, message = "L'identifiant de l'utilisateur ne peut pas être vide")
    private Long user;

    /**
     * Convertit le formulaire en une entité ShopTransactionEntity.
     *
     * @return Une instance de ShopTransactionEntity basée sur les données du formulaire.
     */
    public ShopTransactionEntity toEntity() {
        ShopTransactionEntity entity = new ShopTransactionEntity();
        entity.setCredit(credit);
        return entity;
    }
}
