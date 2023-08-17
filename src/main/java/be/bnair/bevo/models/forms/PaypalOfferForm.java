package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.PaypalOfferEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PaypalOfferForm {
    @NotEmpty(message = "Le titre ne peut pas être vide.")
    @Size(min = 3, message = "Le titre doit contenir au moins 3 caractères")
    private String title;
    @NotEmpty(message = "La description ne peut pas être vide.")
    @Size(min = 3, message = "Le titre doit contenir au moins 3 caractères")
    private String description;
    @Min(value = 1, message = "Le prix ne peut pas être vide")
    private double price;
    @Min(value = 1, message = "Les crédits donnés ne peuvent pas être vide")
    private double credit;

    public PaypalOfferEntity toEntity() {
        PaypalOfferEntity entity = new PaypalOfferEntity();
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setPrice(price);
        entity.setCredit(credit);
        return entity;
    }
}
