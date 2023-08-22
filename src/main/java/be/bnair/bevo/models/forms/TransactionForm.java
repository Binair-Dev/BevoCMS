package be.bnair.bevo.models.forms;

import java.time.LocalDate;

import be.bnair.bevo.models.entities.TransactionEntity;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class TransactionForm {
    @Min(value = 1, message = "Les crédits ne peuvent pas être vide")
    private double credit;
    @Min(value = 1, message = "Le prix ne peut pas être vide")
    private double price;
    @Min(value = 1, message = "Les crédits ne peuvent pas être vide")
    private Long user;

    public TransactionEntity toEntity() {
        TransactionEntity entity = new TransactionEntity();
        entity.setCredit(credit);
        entity.setPrice(price);
        entity.setDate(LocalDate.now());
        return entity;
    }
}
