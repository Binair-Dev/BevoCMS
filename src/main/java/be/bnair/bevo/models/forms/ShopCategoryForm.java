package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.ShopCategoryEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Cette classe représente un formulaire de création de catégorie de boutique.
 *
 * © 2023 Brian Van Bellinghen. Tous droits réservés.
 */
@Data
public class ShopCategoryForm {
    /**
     * Le nom de la catégorie de boutique.
     */
    @NotEmpty(message = "Le nom ne peut pas être vide.")
    @Size(min = 3, message = "Le nom doit contenir au moins 3 caractères")
    private String title;

    /**
     * L'ordre d'affichage de la catégorie.
     */
    @Min(value = 1, message = "L'ordre d'affichage ne peut être vide ou négatif.")
    private int displayOrder;

    /**
     * Convertit le formulaire en une entité ShopCategoryEntity.
     *
     * @return Une instance de ShopCategoryEntity basée sur les données du formulaire.
     */
    public ShopCategoryEntity toEntity() {
        ShopCategoryEntity entity = new ShopCategoryEntity();
        entity.setTitle(title);
        entity.setDisplayOrder(displayOrder);
        return entity;
    }
}
