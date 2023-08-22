package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.ShopTransactionEntity;
import be.bnair.bevo.models.entities.WikiEntity;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ShopTransactionForm {
    @Min(value = 1, message = "L'item's ne peut pas être vide")
    private Long item;
    @Min(value = 1, message = "Les crédits ne peuvent pas être vide")
    private double credit;
    @Min(value = 1, message = "L'utilisateur ne peut pas être vide")
    private Long user;

    public ShopTransactionEntity toEntity() {
        ShopTransactionEntity entity = new ShopTransactionEntity();
        entity.setCredit(credit);
        return entity;
    }
}
