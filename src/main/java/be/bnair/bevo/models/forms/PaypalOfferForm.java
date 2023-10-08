package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.PaypalOfferEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Cette classe représente un formulaire de création d'offre Paypal.
 *
 * @author Brian Van Bellinghen
 */
@Data
public class PaypalOfferForm {
    /**
     * Le titre de l'offre Paypal.
     */
    @NotEmpty(message = "Le titre ne peut pas être vide.")
    @Size(min = 3, message = "Le titre doit contenir au moins 3 caractères")
    private String title;

    /**
     * La description de l'offre Paypal.
     */
    @NotEmpty(message = "La description ne peut pas être vide.")
    @Size(min = 3, message = "La description doit contenir au moins 3 caractères")
    private String description;

    /**
     * Le prix de l'offre Paypal.
     */
    @Min(value = 1, message = "Le prix ne peut pas être vide")
    private double price;

    /**
     * Les crédits donnés par l'offre Paypal.
     */
    @Min(value = 1, message = "Les crédits donnés ne peuvent pas être vide")
    private double credit;

    /**
     * Convertit le formulaire en une entité PaypalOfferEntity.
     *
     * @return Une instance de PaypalOfferEntity basée sur les données du formulaire.
     */
    public PaypalOfferEntity toEntity() {
        PaypalOfferEntity entity = new PaypalOfferEntity();
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setPrice(price);
        entity.setCredit(credit);
        return entity;
    }
}
