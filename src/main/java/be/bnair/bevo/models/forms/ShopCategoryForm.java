package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.ShopCategoryEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ShopCategoryForm {
    @NotEmpty(message = "Le nom ne peut pas être vide.")
    @Size(min = 3, message = "Le nom doit contenir au moins 3 caractères")
    private String title;
    @Min(value = 1, message = "L'ordre d'affichage ne peut être vide ou négatif.")
    private int displayOrder;

    public ShopCategoryEntity toEntity() {
        ShopCategoryEntity entity = new ShopCategoryEntity();
        entity.setTitle(title);
        entity.setDisplayOrder(displayOrder);
        return entity;
    }
}
