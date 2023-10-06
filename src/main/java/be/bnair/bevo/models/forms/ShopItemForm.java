package be.bnair.bevo.models.forms;

import be.bnair.bevo.models.entities.ServerEntity;
import be.bnair.bevo.models.entities.ShopCategoryEntity;
import be.bnair.bevo.models.entities.ShopItemEntity;
import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.utils.constraints.PasswordMatchConstraint;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Cette classe représente un formulaire de création d'article de boutique.
 *
 * Copyright © 2023 Brian Van Bellinghen
 */
@Data
public class ShopItemForm {
    /**
     * Le titre de l'article de boutique.
     */
    @NotEmpty(message = "Le titre ne peut pas être vide.")
    @Size(min = 3, message = "Le titre doit contenir au moins 3 caractères")
    private String title;

    /**
     * La description de l'article de boutique.
     */
    @NotEmpty(message = "La description ne peut pas être vide.")
    @Size(min = 3, message = "La description doit contenir au moins 3 caractères")
    private String description;

    /**
     * L'URL de l'image de l'article de boutique.
     */
    @NotEmpty(message = "L'URL de l'image ne peut pas être vide.")
    @Size(min = 3, message = "L'URL de l'image doit contenir au moins 3 caractères")
    private String image;

    /**
     * L'URL de l'image du contenu de l'article de boutique.
     */
    @NotEmpty(message = "L'URL de l'image du contenu ne peut pas être vide.")
    @Size(min = 3, message = "L'URL de l'image du contenu doit contenir au moins 3 caractères")
    private String contentImage;

    /**
     * Le prix de l'article de boutique.
     */
    @Min(value = 1, message = "Le prix ne peut pas être vide ou négatif")
    private double price;

    /**
     * La commande associée à l'article de boutique.
     */
    @NotEmpty(message = "La commande ne peut pas être vide.")
    @Size(min = 3, message = "La commande doit contenir au moins 3 caractères")
    private String command;

    /**
     * L'identifiant de la catégorie du shop à laquelle l'article de boutique appartient.
     */
    @Min(value = 1, message = "La catégorie du shop ne peut pas être vide")
    private Long shopCategory;

    /**
     * L'identifiant du serveur auquel l'article de boutique est associé.
     */
    @Min(value = 1, message = "Le serveur ne peut pas être vide")
    private Long server;

    /**
     * Convertit le formulaire en une entité ShopItemEntity.
     *
     * @return Une instance de ShopItemEntity basée sur les données du formulaire.
     */
    public ShopItemEntity toEntity() {
        ShopItemEntity shopItemEntity = new ShopItemEntity();
        shopItemEntity.setTitle(title);
        shopItemEntity.setDescription(description);
        shopItemEntity.setImage(image);
        shopItemEntity.setContentImage(contentImage);
        shopItemEntity.setPrice(price);
        shopItemEntity.setCommand(command);
        return shopItemEntity;
    }
}
