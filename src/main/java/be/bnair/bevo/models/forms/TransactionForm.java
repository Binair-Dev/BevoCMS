package be.bnair.bevo.models.forms;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.TransactionEntity;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * Cette classe représente un formulaire de transaction.
 *
 * @author Brian Van Bellinghen
 */
@Data
public class TransactionForm {
    /**
     * Le montant de crédit de la transaction.
     */
    @Min(value = 1, message = "Les crédits ne peuvent pas être vide")
    private double credit;

    /**
     * Le prix de la transaction.
     */
    @Min(value = 1, message = "Le prix ne peut pas être vide")
    private double price;

    /**
     * L'identifiant de l'utilisateur effectuant la transaction.
     */
    @Min(value = 1, message = "L'identifiant de l'utilisateur ne peut pas être vide")
    private Long user;

    /**
     * Convertit le formulaire en une entité TransactionEntity.
     *
     * @return Une instance de TransactionEntity basée sur les données du formulaire.
     */
    public TransactionEntity toEntity() {
        TransactionEntity entity = new TransactionEntity();
        entity.setCredit(credit);
        entity.setPrice(price);
        entity.setDate(LocalDate.now());
        return entity;
    }
}
